package com.onboarding.payu.repository.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

/**
 * Entity that represents a refund object.
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
@Table(name = "refund")
public class Refund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_refund", updatable = false, nullable = false)
	private Integer idRefund;

	@OneToOne
	@JoinColumn(name = "id_payment", updatable = false, nullable = false)
	private Payment payment;

	@Column(name = "reason", length = 1024)
	private String reason;

	@Column(name = "response_json")
	private String response_json;
}
