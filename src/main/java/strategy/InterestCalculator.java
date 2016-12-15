package strategy;

public class InterestCalculator {

    private final InterestCalculationStrategyFactory interestCalculationStrategyFactory = new InterestCalculationStrategyFactory();

    public double calculateInterest(AccountTypes accountType, double accountBalance) {
        InterestCalculationStrategy interestCalculationStrategy = interestCalculationStrategyFactory.getInterestCalculationStrategy(accountType);

        return interestCalculationStrategy.calculateInterest(accountBalance);
    }

    public static void main(String[] args) {
        InterestCalculator interestCalculator = new InterestCalculator();

        System.out.println(interestCalculator.calculateInterest(AccountTypes.CURRENT, 100.00));
        System.out.println(interestCalculator.calculateInterest(AccountTypes.SAVINGS, 100.00));
        System.out.println(interestCalculator.calculateInterest(AccountTypes.STANDARD_MONEY_MARKET, 100.00));
        System.out.println(interestCalculator.calculateInterest(AccountTypes.HIGH_ROLLER_MONEY_MARKET, 100.00));
        System.out.println(interestCalculator.calculateInterest(AccountTypes.HIGH_ROLLER_MONEY_MARKET, 100000.00));
    }
}

enum AccountTypes {CURRENT, SAVINGS, STANDARD_MONEY_MARKET, HIGH_ROLLER_MONEY_MARKET}

interface InterestCalculationStrategy {

    double calculateInterest(double accountBalance);  //Note the absence of an access modifier - interface methods are implicitly public.

}

class CurrentAccountInterestCalculation implements InterestCalculationStrategy {

    @Override
    public double calculateInterest(double accountBalance) {
        return accountBalance * (0.02 / 12);
    }
}

class SavingsAccountInterestCalculation implements InterestCalculationStrategy {

    @Override
    public double calculateInterest(double accountBalance) {
        return accountBalance * (0.04 / 12);
    }
}

class MoneyMarketInterestCalculation implements InterestCalculationStrategy {

    @Override
    public double calculateInterest(double accountBalance) {
        return accountBalance * (0.06/12);
    }
}

class HighRollerMoneyMarketInterestCalculation implements InterestCalculationStrategy {

    @Override
    public double calculateInterest(double accountBalance) {
        return accountBalance < 100000.00 ? 0 : accountBalance * (0.075/12);
    }
}

class NoInterestCalculation implements InterestCalculationStrategy {

    @Override
    public double calculateInterest(double accountBalance) {
        return 0;
    }
}

class InterestCalculationStrategyFactory {

    private final InterestCalculationStrategy currentAccountInterestCalculationStrategy = new CurrentAccountInterestCalculation();
    private final InterestCalculationStrategy savingsAccountInterestCalculationStrategy = new SavingsAccountInterestCalculation();
    private final InterestCalculationStrategy moneyMarketAccountInterestCalculationStrategy = new MoneyMarketInterestCalculation();
    private final InterestCalculationStrategy highRollerMoneyMarketAccountInterestCalculationStrategy = new HighRollerMoneyMarketInterestCalculation();
    private final InterestCalculationStrategy noInterestCalculationStrategy = new NoInterestCalculation();

    //A factory can create a new instance of a class for each request, but since our calculation strategies are stateless,
    //we can hang on to a single instance of each strategy and return that whenever someone asks for it.
    public InterestCalculationStrategy getInterestCalculationStrategy(AccountTypes accountType) {
        switch (accountType) {
            case CURRENT: return currentAccountInterestCalculationStrategy;
            case SAVINGS: return savingsAccountInterestCalculationStrategy;
            case STANDARD_MONEY_MARKET: return moneyMarketAccountInterestCalculationStrategy;
            case HIGH_ROLLER_MONEY_MARKET: return highRollerMoneyMarketAccountInterestCalculationStrategy;
            default: return noInterestCalculationStrategy;
        }
    }
}