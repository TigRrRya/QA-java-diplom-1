package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerPriceParameterizedTest {

    private final float bunPrice;
    private final String bunName;
    private final List<IngredientEntry> ingredientEntries;
    private final float expectedPrice;
    public BurgerPriceParameterizedTest(float bunPrice, String bunName, List<IngredientEntry> ingredientEntries, float expectedPrice) {
        this.bunPrice = bunPrice;
        this.bunName = bunName;
        this.ingredientEntries = ingredientEntries;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "Сценарий #{index}: Ожидаемая цена {3}")
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{

                {300.0f, "red bun", List.of(
                        new IngredientEntry("chili sauce", 300.0f, IngredientType.SAUCE),
                        new IngredientEntry("sausage", 300.0f, IngredientType.FILLING)
                ), 1200.0f},


                {200.0f, "white bun", List.of(
                        new IngredientEntry("sour cream", 200.0f, IngredientType.SAUCE),
                        new IngredientEntry("dinosaur", 200.0f, IngredientType.FILLING),
                        new IngredientEntry("hot sauce", 100.0f, IngredientType.SAUCE)
                ), 900.0f},

                {200.0f, "white bun", List.of(
                        new IngredientEntry("sour cream", 200.0f, IngredientType.SAUCE),
                        new IngredientEntry("dinosaur", 200.0f, IngredientType.FILLING),
                        new IngredientEntry("hot sauce", 100.0f, IngredientType.SAUCE),
                        new IngredientEntry("dinosaur", 200.0f, IngredientType.FILLING),
                        new IngredientEntry("sausage", 300.0f, IngredientType.FILLING)
                ), 1400.0f},

                {100.0f, "black bun", List.of(
                        new IngredientEntry("hot sauce", 100.0f, IngredientType.SAUCE)
                ), 300.0f},

                {100.0f, "black bun", Collections.emptyList(), 200.0f} // 2*100 = 200
        });
    }

    private Burger setupBurger() {
        Burger burger = new Burger();
        Bun mockBun = Mockito.mock(Bun.class);
        when(mockBun.getPrice()).thenReturn(this.bunPrice);
        when(mockBun.getName()).thenReturn(this.bunName);
        burger.setBuns(mockBun);

        for (IngredientEntry entry : this.ingredientEntries) {
            Ingredient mockIng = Mockito.mock(Ingredient.class);
            when(mockIng.getPrice()).thenReturn(entry.price);
            when(mockIng.getName()).thenReturn(entry.name);
            when(mockIng.getType()).thenReturn(entry.type);
            burger.addIngredient(mockIng);
        }
        return burger;
    }

    @Test
    public void getPriceTest() {
        Burger burger = setupBurger();

        assertEquals("Общая цена не соответствует ожидаемой", this.expectedPrice, burger.getPrice(), 0.001f);
    }

    @Test
    public void getReceiptTest() {
        Burger burger = setupBurger();

        StringBuilder expectedReceiptBuilder = new StringBuilder();
        expectedReceiptBuilder.append(String.format("(==== %s ====)%n", this.bunName));

        for (IngredientEntry entry : this.ingredientEntries) {
            String typeName = entry.type.toString().toLowerCase();
            expectedReceiptBuilder.append(String.format("= %s %s =%n", typeName, entry.name));
        }

        expectedReceiptBuilder.append(String.format("(==== %s ====)%n", this.bunName));
        expectedReceiptBuilder.append(String.format("%n"));
        expectedReceiptBuilder.append(String.format("Price: %f%n", this.expectedPrice));

        String expectedReceipt = expectedReceiptBuilder.toString();


        assertEquals("Сравнение формата чека  включая имена, типы и цену",
                expectedReceipt, burger.getReceipt());
    }

    public static class IngredientEntry {

        final String name;
        final float price;
        final IngredientType type;

        public IngredientEntry(String name, float price, IngredientType type) {
            this.name = name;
            this.price = price;
            this.type = type;
        }
    }

}
