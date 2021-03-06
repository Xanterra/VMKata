package com.pillar.eval;

public class VendingMachine {
	
	/**
	 * Stores a vending machine's inventory
	 */
	private Integer numNickels = 0;
	private Integer numDimes = 0;
	private Integer numQuarters = 0;
	private Integer trayNickels = 0;
	private Integer trayDimes = 0;
	private Integer trayQuarters = 0;
	private Integer cokePrice = 100;
	private Integer chipsPrice = 50;
	private Integer candyPrice = 65;
	private Integer cokeStash = 5;
	private Integer chipsStash = 10;
	private Integer candyStash = 15;
	private Integer nickelStash = 5;
	private Integer dimeStash = 5;
	private Integer quarterStash = 5;
	private Boolean previouslySoldOutChecked = false;
	
	private String getValue(){
		if(dimeStash<1&&nickelStash<2){
			return "EXACT CHANGE ONLY";
		}
		if(numNickels==0&&numDimes==0&&numQuarters==0){
			return "INSERT COIN";
		}
		else{
			return String.valueOf(getIntValue());
		}
	}
	private void vendChange(Integer selectionValue){
		Integer remaining = getIntValue()-selectionValue;
		while (remaining>0){
			if(numQuarters>0&&remaining>=25){
				numQuarters--;
				trayQuarters++;//return a quarter
				remaining -= 25;
			}
			if(numDimes>0&&remaining>=10){
				numDimes--;
				trayDimes++;//return a dime
				remaining -= 10;
			}
			if(numNickels>0&&remaining>=5){
				numNickels--;
				trayNickels++;//return a nickel
				remaining -= 5;
			}
			if((remaining<25&&numDimes==0&&numNickels<2)||(remaining<10&&numNickels==0)){
				if(dimeStash>0&&remaining>=10){
					dimeStash--;
					trayDimes++;//return a dime
					remaining -= 10;
				}
				if(nickelStash>0&&remaining>=5){
					nickelStash--;
					trayNickels++;//return a nickel
					remaining -= 5;
				}
				else break;
			}
		}
		quarterStash += numQuarters;
		dimeStash += numDimes;
		nickelStash += numNickels;
		numQuarters = 0;
		numDimes = 0;
		numNickels = 0;
		previouslySoldOutChecked = false;
	}
	private Integer getIntValue(){
		return((numNickels*5)+(numDimes)*10+(numQuarters*25));
	}
	public void returnCoins(){
		/**
		 * Returns all inserted coins to the coin tray. They can be 
		 * accessed using getCoins now.
		 */
		vendChange(0);
	}
	public String insert(Integer weight, Integer diameter) {
		/**
		 * @return total value
		 */
		if(weight==2&diameter==2){
			numNickels++;
			return getValue();
		}
		if(weight==2&diameter==1){
			numDimes++;
			return getValue();
		}
		if(weight==3&diameter==3){
			numQuarters++;
			return getValue();
		}
		else return "INVALID COIN";
	}
	public Integer[] getCoins(){
		/**
		 * @return coins currently in coin tray. Also clears the coin tray.
		 */
		Integer[] A = {trayQuarters,trayDimes,trayNickels};
		trayQuarters = 0;
		trayDimes = 0;
		trayNickels = 0;
		return A;
	}
	public String insert() {
		/**
		 * @return total value or INSERT COIN if 0 or EXACT CHANGE REQUIRED if required
		 */
		return getValue();
	}
	public String select(Integer selection) {
		/**
		 * @return THANK YOU if product dispensed
		 * Adds change to the tray. If adequate change cannot be dispensed,
		 * the machine will dispense as much as possible without exceeding
		 * the amount due.
		 */
		if(selection==1){
			if(cokeStash>0){
				if(getIntValue()>=cokePrice){
					vendChange(cokePrice);
					cokeStash--;
					return "THANK YOU";
				}
				else return "PRICE "+cokePrice+" CENTS";
			}
			else if (previouslySoldOutChecked) return getValue();
			else {
				previouslySoldOutChecked = true;
				return "SOLD OUT";
			}
		}
		else if(selection==2){
			if(chipsStash>0){
				if(getIntValue()>=chipsPrice){
					vendChange(chipsPrice);
					chipsStash--;
					return "THANK YOU";
				}
				else return "PRICE "+chipsPrice+" CENTS";
			}
			else if (previouslySoldOutChecked) return getValue();
			else {
				previouslySoldOutChecked = true;
				return "SOLD OUT";
			}
		}
		else if(selection==3){
			if(candyStash>0){
				if(getIntValue()>=candyPrice){
					vendChange(candyPrice);
					candyStash--;
					return "THANK YOU";
				}
				else return "PRICE "+candyPrice+" CENTS";
			}
			else if (previouslySoldOutChecked) return getValue();
			else {
				previouslySoldOutChecked = true;
				return "SOLD OUT";
			}
		}
		else return "INVALID PRODUCT SELECTION";
	}
	
}
