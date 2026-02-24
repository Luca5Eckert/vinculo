package com.vinculo.module.post.infrastructure.persistence;

import com.vinculo.module.post.domain.model.Post;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryNeo4j extends Neo4jRepository<Post, Long> {

    @Query("""
        MATCH (me:Person) WHERE id(me) = $personId
        
        MATCH (me)-[connections:CONNECTED_WITH*1..2]-(author:Person)-[:POSTED]->(post:Post)
        WHERE author <> me
        
        WITH post, author, reduce(totalWeight = 0, rel IN connections | totalWeight + rel.weight) AS pathWeight
        
        RETURN post, author
        
        ORDER BY pathWeight ASC, post.createdAt DESC
        
        SKIP $skip LIMIT $limit
        """)
    List<Post> findNetworkFeed(
            @Param("personId") Long personId,
            @Param("limit") int limit,
            @Param("skip") int skip
    );

}
