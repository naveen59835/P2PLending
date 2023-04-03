package com.stackroute.recommendation.domain;

import lombok.*;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
//import org.neo4j.ogm.annotation.Id;
//import org.neo4j.ogm.annotation.NodeEntity;
//import org.neo4j.ogm.annotation.Relationship;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Node("Borrower")
public class RecommendedBorrower {
   @Id
    private String name;


    @Relationship(type="Has", direction = Relationship.Direction.OUTGOING)
    private CibilScore cibilScore;
    private int creditScore;
    private String borrowerId;

}
