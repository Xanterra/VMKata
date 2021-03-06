package com.pillar.eval;
import org.junit.Test;
import org.junit.Before;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertEquals;

public class VendingMachineTest {
	
	/*Accept Coins*/
	@Test
	public void whenCoinsAreInsertedInsertCoinIsNotDisplayed() {
		VendingMachine vm = new VendingMachine();
		assertFalse(vm.insert(2,2).equals("INSERT COIN"));
	}
	
	@Test
	public void whenCoinsAreNotInsertedInsertCoinIsDisplayed() {
		VendingMachine vm = new VendingMachine();
		assertEquals(vm.insert(), "INSERT COIN");
	}
	
	@Test
	public void whenNickelIsInsertedValueIncreasesByFive() {
		VendingMachine vm = new VendingMachine();
		assertEquals("5", vm.insert(2,2));
		assertEquals("10", vm.insert(2,2));
	}
	@Test
	public void whenDimeIsInsertedValueIncreasesByTen() {
		VendingMachine vm = new VendingMachine();
		assertEquals("10", vm.insert(2,1));
		assertEquals("20", vm.insert(2,1));
	}
	
	@Test
	public void whenQuarterIsInsertedValueIncreasesBy25() {
		VendingMachine vm = new VendingMachine();
		assertEquals("25", vm.insert(3,3));
		assertEquals("50", vm.insert(3,3));
	}
	@Test
	public void whenPennyIsInsertedReturnsErrorMessage() {
		VendingMachine vm = new VendingMachine();
		assertEquals("INVALID COIN", vm.insert(1,1));
	}
	
	/* Select Product */
	@Test
	public void whenColaIsSelectedButNotEnoughPaidPriceDisplayed() {
		VendingMachine vm = new VendingMachine();
		for(int i=0; i<=2; i++){vm.insert(3,3);}
		assertEquals("PRICE 100 CENTS", vm.select(1));
	}
	@Test
	public void whenColaIsSelectedAndProperlyPaidCokeDispensedAndValueReset() {
		VendingMachine vm = new VendingMachine();
		for(int i=0; i<=3; i++){vm.insert(3,3);}
		assertEquals("THANK YOU", vm.select(1));
		assertEquals("INSERT COIN", vm.insert());
	}
	@Test
	public void whenChipsIsSelectedButNotEnoughPaidPriceDisplayed() {
		VendingMachine vm = new VendingMachine();
		vm.insert(3,3);
		assertEquals("PRICE 50 CENTS", vm.select(2));
	}
	@Test
	public void whenChipsIsSelectedAndProperlyPaidChipsDispensedAndValueReset() {
		VendingMachine vm = new VendingMachine();
		vm.insert(3,3);
		vm.insert(3,3);
		assertEquals("THANK YOU", vm.select(2));
		assertEquals("INSERT COIN", vm.insert());
	}
	public void whenCandyIsSelectedButNotEnoughPaidPriceDisplayed() {
		VendingMachine vm = new VendingMachine();
		vm.insert(3,3);
		assertEquals("PRICE 65 CENTS", vm.select(2));
	}
	@Test
	public void whenCandyIsSelectedAndProperlyPaidCandyDispensedAndValueReset() {
		VendingMachine vm = new VendingMachine();
		vm.insert(3,3);
		vm.insert(3,3);
		vm.insert(2,1);
		vm.insert(2,2);
		assertEquals("THANK YOU", vm.select(2));
		assertEquals("INSERT COIN", vm.insert());
	}
	
	/* Make Change*/
	@Test
	public void whenTooMuchIsPaidForChipsValueIsProperlyReset() {
		VendingMachine vm = new VendingMachine();
		vm.insert(3,3);
		vm.insert(3,3);
		vm.insert(2,1);
		vm.insert(2,2);
		assertEquals("THANK YOU", vm.select(2));
		assertEquals("INSERT COIN", vm.insert());
		Integer[] tempReturn = new Integer[3];
		tempReturn = vm.getCoins();
		assertEquals(tempReturn[0], (Integer) 0);
		assertEquals(tempReturn[1], (Integer) 1);
		assertEquals(tempReturn[2], (Integer) 1);
	}
	
	/* Return Coins */
	@Test
	public void whenCoinsAreInsertedAndReturnRequestedTheyAreProperlyReturned() {
		VendingMachine vm = new VendingMachine();
		vm.insert(3,3);
		vm.insert(2,1);
		vm.insert(2,2);
		vm.returnCoins();
		Integer[] tempReturn = new Integer[3];
		tempReturn = vm.getCoins();
		assertEquals(tempReturn[0], (Integer) 1);
		assertEquals(tempReturn[1], (Integer) 1);
		assertEquals(tempReturn[2], (Integer) 1);
	}
	@Test
	public void whenNothingIsInsertedReturnCoinsReturnsNothing() {
		VendingMachine vm = new VendingMachine();
		vm.returnCoins();
		Integer[] tempReturn = new Integer[3];
		tempReturn = vm.getCoins();
		assertEquals(tempReturn[0], (Integer) 0);
		assertEquals(tempReturn[1], (Integer) 0);
		assertEquals(tempReturn[2], (Integer) 0);
	}
	@Test
	public void whenCoinsAreReturnedVendingMachineProperlyResetsCredit() {
		VendingMachine vm = new VendingMachine();
		vm.insert(3,3);
		vm.insert(2,1);
		vm.insert(2,2);
		vm.returnCoins();
		assertEquals("INSERT COIN", vm.insert());
	}
	
	/* Sold Out*/
	@Test
	public void whenDisplayIsCheckedAndItemIsOutOfStockSoldOutWillBeDisplayed() {
		VendingMachine vm = new VendingMachine();
		for(int i=0; i<=4; i++){
			for(int j=0; j<=3; j++){
				vm.insert(3,3);
			}
			assertEquals(vm.select(1), "THANK YOU");
		}
		assertEquals(vm.select(1), "SOLD OUT");
	}
	@Test
	public void whenDisplayIsCheckedASecondTimeInsertCoinIsDisplayed() {
		VendingMachine vm = new VendingMachine();
		for(int i=0; i<=4; i++){
			for(int j=0; j<=3; j++){
				vm.insert(3,3);
			}
			assertEquals(vm.select(1), "THANK YOU");
		}
		assertEquals(vm.select(1), "SOLD OUT");
		assertEquals("INSERT COIN", vm.select(1));
	}
	
	/* Exact Change Only */
	@Test
	public void whenMachineCannotMakeChangeItWillDisplayExactChangeOnly() {
		VendingMachine vm = new VendingMachine();
		for(int i=0; i<=7; i++){
			for(int j=0; j<=2; j++){
				vm.insert(3,3);
			}
			assertEquals("THANK YOU", vm.select(3));
		}
		assertEquals("EXACT CHANGE ONLY", vm.insert());
	}
}
