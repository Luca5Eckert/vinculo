package com.vinculo.module.post.infrastructure.persistence;

import com.vinculo.module.post.domain.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryNeo4j extends Neo4jRepository<Post, Long> {

    @Query("""
            MATCH (me:Person {id: $personId})-[c:CONNECTED_WITH]->(author:Person)
            MATCH (author)-[rel:POSTED]->(p:Post)
            RETURN p, rel, author
            ORDER BY c.weight ASC, p.createdAt DESC
            SKIP $pageable.offset LIMIT $pageable.pageSize
            """)
    List<Post> findNetworkFeed(
            @Param("personId") Long personId,
            @Param("pageable") Pageable pageable
    );

    @Query(value = "MATCH (p:Post)-[:AUTHORED_BY]->(a:Person {id: $authorId}) RETURN p",
            countQuery = "MATCH (p:Post)-[:AUTHORED_BY]->(a:Person {id: $authorId}) RETURN count(p)")
    Page<Post> findAllByAuthorId(Long authorId, Pageable pageable);

}