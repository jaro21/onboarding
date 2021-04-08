package com.onboarding.payu.repository.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity that represents a Customer object.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_customer", updatable = false, nullable = false)
	private Integer idCustomer;

	@Column(name = "full_name", length = 45)
	private String fullName;

	@Column(name = "email", length = 45)
	private String email;

	@Column(name = "phone", length = 45)
	private String phone;

	@Column(name = "dni_number", length = 45)
	private String dniNumber;

	@Column(name = "active", length = 1)
	private Integer active;

	@Column(name = "address", length = 200)
	private String address;

	@Column(name = "city", length = 50)
	private String city;

	@Column(name = "state", length = 50)
	private String state;

	@Column(name = "country", length = 50)
	private String country;

	@Column(name = "postal_code", length = 5)
	private String postal_code;

	@Column(name = "username", length = 45)
	private String username;

	@Column(name = "password_hash", length = 45)
	private String password_hash;

	@Column(name = "id_rol")
	private Integer idRol;

	@OneToMany(mappedBy = "idCustomer", fetch = FetchType.EAGER)
	private List<CreditCard> creditCardList;
}
