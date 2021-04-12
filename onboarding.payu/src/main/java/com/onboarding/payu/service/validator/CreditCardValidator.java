package com.onboarding.payu.service.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.CreditCartType;
import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import org.springframework.stereotype.Component;

/**
 * Credit card validator
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CreditCardValidator {

	public void runValidations(final CreditCardRequest creditCardRequest) {

		isPeriodValid(creditCardRequest.getExpirationDate());
		isPaymentMethodValid(creditCardRequest);
	}

	private void isPaymentMethodValid(final CreditCardRequest creditCardRequest) {

		final Optional<CreditCartType> creditCartType = getPaymentMethod(creditCardRequest.getNumber());
		if (!creditCartType.isPresent()) {
			throw new BusinessAppException(ExceptionCodes.CREDIT_CARD_INVALID);
		}
		if (!creditCartType.get().getNameCard().equals(creditCardRequest.getPaymentMethod())) {
			throw new BusinessAppException(ExceptionCodes.PAYMENT_METHOD_IVALID, creditCardRequest.getPaymentMethod());
		}
	}

	public void isPeriodValid(final String period) {

		final LocalDate periodo = stringToLocalDate(period);
		if (periodo.isBefore(LocalDate.now())) {
			throw new BusinessAppException(ExceptionCodes.PERIOD_INVALID);
		}
	}

	public LocalDate stringToLocalDate(final String period) {

		try {
			DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy/MM")
																		.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
																		.toFormatter();
			return LocalDate.parse(period, formatter);
		} catch (final DateTimeParseException e) {
			throw new BusinessAppException(ExceptionCodes.PERIOD_FORMAT_INVALID);
		}
	}

	public Optional<CreditCartType> getPaymentMethod(final String cardNumber) {

		for (CreditCartType creditCartType : CreditCartType.values()) {
			Pattern patron = Pattern.compile(creditCartType.getPattern());
			Matcher matcher = patron.matcher(cardNumber);

			if (matcher.find()) {
				return Optional.of(creditCartType);
			}
		}

		return Optional.empty();
	}
}
