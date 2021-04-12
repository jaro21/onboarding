package com.onboarding.payu.service.impl;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.customer.request.CustomerRequest;
import com.onboarding.payu.model.customer.response.CustomerResponse;
import com.onboarding.payu.repository.ICustomerRepository;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.service.ICustomerService;
import com.onboarding.payu.service.impl.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ICustomerService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

	private final ICustomerRepository iCustomerRepository;

	private final CustomerMapper customerMapper;

	@Autowired
	public CustomerServiceImpl(final ICustomerRepository iCustomerRepository, final CustomerMapper customerMapper) {

		this.iCustomerRepository = iCustomerRepository;
		this.customerMapper = customerMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Customer findByDniNumber(final String dniNumber) {

		return iCustomerRepository.findByDniNumber(dniNumber).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.CUSTOMER_NUMBER_NOT_EXIST, dniNumber));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Customer findByUsername(final String username) {

		return iCustomerRepository.findByUsername(username).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.USERNAME_NOT_EXIST, username));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Customer findById(final Integer idCustomer) {

		return iCustomerRepository.findById(idCustomer).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.CUSTOMER_ID_NOT_EXIST, idCustomer.toString()));
	}

	@Override public CustomerResponse findCustomerById(final Integer idCustomer) {

		final Customer customer = findById(idCustomer);
		return customerMapper.toCustomerResponse(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public CustomerResponse save(final CustomerRequest customerRequest) {

		createValidation(customerRequest);
		final Customer customer = iCustomerRepository.save(customerMapper.toCustomer(customerRequest));
		return customerMapper.toCustomerResponse(customer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public CustomerResponse update(final CustomerRequest customerRequest) {

		updateValidation(customerRequest);
		final Customer customer = iCustomerRepository.save(customerMapper.toCustomer(customerRequest));
		return customerMapper.toCustomerResponse(customer);
	}

	private void updateValidation(final CustomerRequest customerRequest) {

		findById(customerRequest.getIdCustomer());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void delete(final Integer idCustomer) {

		try {
			findById(idCustomer);
			iCustomerRepository.deleteById(idCustomer);
		} catch (DataIntegrityViolationException e) {
			log.error("Failed to delete customer id {} ", idCustomer, e);
			throw new BusinessAppException(ExceptionCodes.ERROR_TO_DELETE_CUSTOMER);
		}
	}

	/**
	 * Run validations on the product to create
	 *
	 * @param customerRequest {@link CustomerRequest}
	 */
	private void createValidation(final CustomerRequest customerRequest) {

		if (iCustomerRepository.findByDniNumber(customerRequest.getDniNumber()).isPresent()) {
			throw new BusinessAppException(ExceptionCodes.DUPLICATE_CUSTOMER_DNI, customerRequest.getDniNumber());
		}
		if (iCustomerRepository.findByUsername(customerRequest.getUsername()).isPresent()) {
			throw new BusinessAppException(ExceptionCodes.DUPLICATE_USERNAME, customerRequest.getUsername());
		}
	}
}
