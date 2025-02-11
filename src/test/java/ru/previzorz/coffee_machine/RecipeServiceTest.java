package ru.previzorz.coffee_machine;

import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.previzorz.coffee_machine.dto.CoffeeDTO;
import ru.previzorz.coffee_machine.dto.IngredientDTO;
import ru.previzorz.coffee_machine.dto.IngredientProportionDTO;
import ru.previzorz.coffee_machine.dto.RecipeDTO;
import ru.previzorz.coffee_machine.entity.Coffee;
import ru.previzorz.coffee_machine.entity.Ingredient;
import ru.previzorz.coffee_machine.entity.Recipe;
import ru.previzorz.coffee_machine.repository.CoffeeRepository;
import ru.previzorz.coffee_machine.repository.IngredientRepository;
import ru.previzorz.coffee_machine.service.RecipeService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private CoffeeRepository coffeeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private RecipeService recipeService;

    private static CoffeeDTO getCoffeeDTO() {
        CoffeeDTO coffeeDTO = new CoffeeDTO();
        coffeeDTO.setName("латте");

        List<IngredientProportionDTO> ingredientProportionDTOList = new ArrayList<>();
        IngredientDTO ingredientDTO1 = new IngredientDTO();
        ingredientDTO1.setName("молоко");

        IngredientDTO ingredientDTO2 = new IngredientDTO();
        ingredientDTO2.setName("вода");

        IngredientProportionDTO ingredientProportionDTO1 = new IngredientProportionDTO();
        ingredientProportionDTO1.setProportion(100);
        ingredientProportionDTO1.setIngredient(ingredientDTO1);

        IngredientProportionDTO ingredientProportionDTO2 = new IngredientProportionDTO();
        ingredientProportionDTO2.setProportion(50);
        ingredientProportionDTO2.setIngredient(ingredientDTO2);

        ingredientProportionDTOList.add(ingredientProportionDTO1);
        ingredientProportionDTOList.add(ingredientProportionDTO2);

        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setIngredientProportions(ingredientProportionDTOList);
        coffeeDTO.setRecipe(recipeDTO);

        return coffeeDTO;
    }

    @Test
    void addNewRecipe_ShouldSaveNewCoffee() {
        CoffeeDTO coffeeDTO = getCoffeeDTO();

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("молоко");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("вода");

        when(ingredientRepository.findByName_In(Set.of("молоко", "вода")))
                .thenReturn(List.of(ingredient1, ingredient2));

        Coffee savedCoffee = new Coffee();
        savedCoffee.setName(coffeeDTO.getName().toLowerCase());
        savedCoffee.setRecipe(new Recipe());

        when(coffeeRepository.save(any(Coffee.class))).thenReturn(savedCoffee);

        Coffee result = recipeService.addNewRecipe(coffeeDTO);

        assertNotNull(result);
        assertEquals("латте", result.getName());
        assertNotNull(result.getRecipe());
        verify(coffeeRepository, times(1)).save(any(Coffee.class));
    }

    @Test
    void addNewRecipe_ShouldThrowEntityExistsException_WhenCoffeeExists() {
        CoffeeDTO coffeeDTO = getCoffeeDTO();

        String coffeeName = "латте";
        Coffee existingCoffee = new Coffee();
        existingCoffee.setName(coffeeName.toLowerCase());

        when(coffeeRepository.findByName(coffeeName.toLowerCase()))
                .thenReturn(Optional.of(existingCoffee));

        RecipeService recipeService = new RecipeService(coffeeRepository, ingredientRepository);

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            recipeService.addNewRecipe(coffeeDTO);  // This will internally call validateDoesCoffeeExist
        });

        assertEquals("Рецепт для латте уже создан", exception.getMessage());
    }


    @Test
    void addNewRecipe_ShouldNotThrowEntityExistsException_WhenCoffeeDoesNotExist() {
        CoffeeDTO coffeeDTO = getCoffeeDTO();

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("молоко");

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("вода");

        when(coffeeRepository.findByName("латте")).thenReturn(Optional.empty());

        when(ingredientRepository.findByName_In(Set.of("молоко", "вода")))
                .thenReturn(List.of(ingredient1, ingredient2));

        Coffee savedCoffee = new Coffee();
        savedCoffee.setName("латте");
        when(coffeeRepository.save(any(Coffee.class))).thenReturn(savedCoffee);

        Coffee result = recipeService.addNewRecipe(coffeeDTO);

        assertNotNull(result);
        assertEquals("латте", result.getName());
        verify(coffeeRepository, times(1)).save(any(Coffee.class));
    }

}