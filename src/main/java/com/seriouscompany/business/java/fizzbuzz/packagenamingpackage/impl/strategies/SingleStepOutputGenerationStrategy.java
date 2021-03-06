package com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.strategies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.BuzzStrategyFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.BuzzStringPrinterFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.FizzBuzzOutputGenerationContextVisitorFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.FizzStrategyFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.FizzStringPrinterFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.IntegerIntegerPrinterFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.NewLineStringPrinterFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.factories.NoFizzNoBuzzStrategyFactory;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.impl.visitors.FizzBuzzOutputGenerationContext;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.interfaces.printers.StringPrinter;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.interfaces.strategies.OutputGenerationStrategy;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.interfaces.strategies.SingleStepOutputGenerationParameter;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.interfaces.visitors.OutputGenerationContext;
import com.seriouscompany.business.java.fizzbuzz.packagenamingpackage.interfaces.visitors.OutputGenerationContextVisitor;

@Service
public class SingleStepOutputGenerationStrategy implements OutputGenerationStrategy {

	private List<OutputGenerationContext> contexts = new ArrayList<OutputGenerationContext>();
	private OutputGenerationContextVisitor contextVisitor;

	private StringPrinter myNewLinePrinter;
	
	@Autowired
	public SingleStepOutputGenerationStrategy(FizzBuzzOutputGenerationContextVisitorFactory fizzBuzzOutputGenerationContextVisitorFactory,
			FizzStrategyFactory fizzStrategyFactory, 
			FizzStringPrinterFactory fizzStringPrinterFactory, 
			BuzzStrategyFactory buzzStrategyFactory, 
			BuzzStringPrinterFactory buzzStringPrinterFactory,
			NoFizzNoBuzzStrategyFactory noFizzNoBuzzStrategyFactory,
			IntegerIntegerPrinterFactory integerIntegerPrinterFactory,
			NewLineStringPrinterFactory newLineStringPrinterFactory) {
		contextVisitor = fizzBuzzOutputGenerationContextVisitorFactory.createVisitor();
		contexts.add(new FizzBuzzOutputGenerationContext(fizzStrategyFactory.createIsEvenlyDivisibleStrategy(), fizzStringPrinterFactory.createStringPrinter()));
		contexts.add(new FizzBuzzOutputGenerationContext(buzzStrategyFactory.createIsEvenlyDivisibleStrategy(), buzzStringPrinterFactory.createStringPrinter()));
		contexts.add(new FizzBuzzOutputGenerationContext(noFizzNoBuzzStrategyFactory.createIsEvenlyDivisibleStrategy(), integerIntegerPrinterFactory.createPrinter()));

		myNewLinePrinter = newLineStringPrinterFactory.createStringPrinter();	}

	public void performGenerationForCurrentStep(SingleStepOutputGenerationParameter generationParameter) {
		final int nGenerationParameter = generationParameter.retrieveIntegerValue();
		Iterator<OutputGenerationContext> iterator = contexts.iterator();
		while(iterator.hasNext()) {
			OutputGenerationContext context = iterator.next();
			contextVisitor.visit(context, nGenerationParameter);
		}
		myNewLinePrinter.print();
	}
}
