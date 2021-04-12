package com.onboarding.payu.service;

import com.onboarding.payu.model.customer.request.CustomerRequest;
import com.onboarding.payu.model.customer.response.CustomerResponse;
import com.onboarding.payu.repository.entity.Customer;

/**
 * Interface that define of Customer's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ICustomerService {

	/**
	 * Method to get Customer by dniNumber
	 *
	 * @param dniNumber {@link String}
	 * @return {@link Customer}
	 * @
	 */
	Customer findByDniNumber(String dniNumber) ;

	/**
	 * Method to get Customer by username
	 *
	 * @param username {@link String}
	 * @return {@link Customer}
	 * @
	 */
	Customer findByUsername(String username) ;

	/**
	 * Method to get Customer by idCustomer
	 *
	 * @param idCustomer {@link Integer}
	 * @return {@link Customer}
	 * @
	 */
	Customer findById(Integer idCustomer) ;

	/**
	 * Method to get Customer by idCustomer
	 *
	 * @param idCustomer {@link Integer}
	 * @return {@link Customer}
	 * @
	 */
	CustomerResponse findCustomerById(Integer idCustomer) ;

	/**
	 * Method to create a Customer
	 *
	 * @param customerRequest {@link CustomerRequest}
	 * @return {@link CustomerResponse}
	 */
	CustomerResponse save(CustomerRequest customerRequest);

	/**
	 * Method to create a Customer
	 *
	 * @param customerRequest {@link CustomerRequest}
	 * @return {@link CustomerResponse}
	 */
	CustomerResponse update(CustomerRequest customerRequest);

	/**
	 * Method to delete a Customer by id
	 *
	 * @param idCustomer
	 */
	void delete(Integer idCustomer);
}
