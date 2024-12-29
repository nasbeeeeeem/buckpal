package io.reflectoring.buckpal.adapter.out.presistence;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityJpaEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Column
  private LocalDateTime timestamp;

  @Column
	private long ownerAccountId;

	@Column
	private long sourceAccountId;

	@Column
	private long targetAccountId;

	@Column
	private long amount;
}
