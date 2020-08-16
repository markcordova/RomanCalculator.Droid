# RomanCalculator.Droid

This is one of my early (circa 2010) explorartory Android projects. This is meant only for educational purposes and has no real practical use, unless of course you work on Roman numerals.

Some ideas explored in this project that might be interesting:
* A calculator 'Activity' will by nature have lots of inputs (and outputs) that need to be managed. Instead of implementing a 'god-class' activity, this is broken up into smaller [controllers] instead.
* Converting to and from Roman Numerals has it's "quirks" and is implemented in the [RomanNumeralsConverter] class.
* Calculation is implemented by parsing the mathematical expression using the [Shunting-yard algorithm] to produce a postfix notation which is subsequently evaluated. This is implemented in the [CalculatorCore] class.
* All mathematical operations are abstracted from the core and are defined in the [CalculatorOperations] class. This makes it very easy to add more operations as needed.

[controllers]:https://github.com/markcordova/RomanCalculator.Droid/tree/master/src/com/mcordova/android/solutions/romancalc/controller
[RomanNumeralsConverter]:https://github.com/markcordova/RomanCalculator.Droid/blob/master/src/com/mcordova/android/solutions/romancalc/model/RomanNumeralsConverter.java
[Shunting-yard algorithm]:https://en.wikipedia.org/wiki/Shunting-yard_algorithm
[CalculatorCore]:https://github.com/markcordova/RomanCalculator.Droid/blob/master/src/com/mcordova/android/solutions/romancalc/model/CalculatorCore.java
[CalculatorOperations]:https://github.com/markcordova/RomanCalculator.Droid/blob/master/src/com/mcordova/android/solutions/romancalc/model/CalculatorOperations.java
