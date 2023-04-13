package com.stackroute.recommendation.domain;

import lombok.*;

import org.springframework.data.neo4j.core.schema.*;
//import org.neo4j.ogm.annotation.Id;
//import org.neo4j.ogm.annotation.NodeEntity;
//import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.core.schema.IdGenerator;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Node("Borrower")
public class RecommendedBorrower {
   @Id  private String id;
   @Relationship(type="Has", direction = Relationship.Direction.OUTGOING)
   private CibilScore cibilScore;
   private int creditScore;
   private String borrowerId;
   private double amount;

}
