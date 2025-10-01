package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    private Burger burger;

    @Mock
    Bun mockBun;

    @Mock
    Ingredient mockSauce;

    @Mock
    Ingredient mockFilling;

    @Before
    public void setUp() {
        burger = new Burger();
    }


    @Test
    public void setBunsTest() {
        burger.setBuns(mockBun);
        assertEquals("поле 'bun' = mockBun", mockBun, burger.bun);

    }

    @Test
    public void addIngredientTest() {
        assertEquals("список ингредиентов пуст", 0, burger.ingredients.size());

        burger.addIngredient(mockSauce);
        assertEquals("Добавили 1 ингредиент, размер списка стал 1", 1, burger.ingredients.size());
        assertEquals("Первый элемент списка mockSauce", mockSauce, burger.ingredients.get(0));

        burger.addIngredient(mockFilling);
        assertEquals("добавили 2й, размер списка 2", 2, burger.ingredients.size());
        assertEquals("Второй элемент в списке mockFilling", mockFilling, burger.ingredients.get(1));
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);
        assertEquals("В списке 2 элемента", 2, burger.ingredients.size());

        burger.removeIngredient(0);
        assertEquals("После удаление 1 элемент в списке", 1, burger.ingredients.size());
        assertEquals("Элемент на 0 индексе mockFilling", mockFilling, burger.ingredients.get(0));
    }

    @Test
    public void removeOnlyOneIngredientTest() {
        burger.addIngredient(mockFilling);
        burger.removeIngredient(0);
        assertEquals("Список пустой", 0, burger.ingredients.size());
    }

    @Test
    public void moveIngredientTest() {
        Ingredient mockCheese = Mockito.mock(Ingredient.class);


        burger.addIngredient(mockSauce);
        burger.addIngredient(mockCheese);
        burger.addIngredient(mockFilling);

        assertEquals("В списке 3 элемента", 3, burger.ingredients.size());


        assertEquals("Порядок элементов в списке индекс 0", mockSauce, burger.ingredients.get(0));
        assertEquals("Порядок элементов в списке индекс 1", mockCheese, burger.ingredients.get(1));
        assertEquals("Порядок элементов в списке индекс 2", mockFilling, burger.ingredients.get(2));


        burger.moveIngredient(2, 0);


        assertEquals("Элемент на индексе 0 должен быть mockFilling (перемещенный)",
                mockFilling, burger.ingredients.get(0));

        assertEquals("Элемент на индексе 1 должен быть mockSauce (сдвинутый)",
                mockSauce, burger.ingredients.get(1));

        assertEquals("Элемент на индексе 2 должен быть mockCheese (сдвинутый)",
                mockCheese, burger.ingredients.get(2));
    }


}