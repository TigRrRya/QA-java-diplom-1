package praktikum;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {

    @Mock
    Bun mockBun;
    @Mock
    Ingredient mockSauce;
    @Mock
    Ingredient mockFilling;
    private Burger burger;
    private SoftAssertions softAssertions;

    @Before
    public void setUp() {
        burger = new Burger();
        softAssertions = new SoftAssertions();
    }


    @Test
    public void setBunsTest() {
        burger.setBuns(mockBun);
        assertEquals("Поле 'bun' должно быть установлено", mockBun, burger.bun);
    }


    @Test
    public void addIngredientTest() {
        softAssertions.assertThat(burger.ingredients.size())
                .as("Список ингредиентов должен быть пустым")
                .isEqualTo(0);

        burger.addIngredient(mockSauce);
        softAssertions.assertThat(burger.ingredients.size())
                .as("После добавления соуса, размер списка должен стать 1")
                .isEqualTo(1);
        softAssertions.assertThat(burger.ingredients.get(0))
                .as("Первый элемент списка должен быть mockSauce")
                .isEqualTo(mockSauce);

        burger.addIngredient(mockFilling);
        softAssertions.assertThat(burger.ingredients.size())
                .as("После добавления второго элемента, размер списка должен стать 2")
                .isEqualTo(2);
        softAssertions.assertThat(burger.ingredients.get(1))
                .as("Второй элемент в списке должен быть mockFilling")
                .isEqualTo(mockFilling);

        softAssertions.assertAll();
    }


    @Test
    public void removeIngredientTest() {
        burger.addIngredient(mockSauce);
        burger.addIngredient(mockFilling);

        burger.removeIngredient(0);

        softAssertions.assertThat(burger.ingredients.size())
                .as("После удаления 1 элемент в списке")
                .isEqualTo(1);
        softAssertions.assertThat(burger.ingredients.get(0))
                .as("Элемент на 0 индексе должен быть mockFilling (сдвинутый)")
                .isEqualTo(mockFilling);

        softAssertions.assertAll();
    }


    @Test
    public void removeOnlyOneIngredientTest() {
        burger.addIngredient(mockFilling);
        burger.removeIngredient(0);
        assertEquals("Список должен стать пустым", 0, burger.ingredients.size());
    }


    @Test
    public void moveIngredientTest() {
        Ingredient mockCheese = Mockito.mock(Ingredient.class);

        burger.addIngredient(mockSauce);
        burger.addIngredient(mockCheese);
        burger.addIngredient(mockFilling);

        softAssertions.assertThat(burger.ingredients.size())
                .as("В списке должно быть 3 элемента перед перемещением")
                .isEqualTo(3);

        burger.moveIngredient(2, 0);

        softAssertions.assertThat(burger.ingredients.get(0))
                .as("Элемент на индексе 0 должен быть mockFilling (перемещенный)")
                .isEqualTo(mockFilling);

        softAssertions.assertThat(burger.ingredients.get(1))
                .as("Элемент на индексе 1 должен быть mockSauce (сдвинутый)")
                .isEqualTo(mockSauce);

        softAssertions.assertThat(burger.ingredients.get(2))
                .as("Элемент на индексе 2 должен быть mockCheese (сдвинутый)")
                .isEqualTo(mockCheese);

        softAssertions.assertAll();
    }
}