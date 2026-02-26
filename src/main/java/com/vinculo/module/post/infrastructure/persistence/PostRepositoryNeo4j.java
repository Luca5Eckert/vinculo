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
public interface PostRepositoryNeo4j extends Neo4jRepository<Post, String> {

    @Query(value = """
            MATCH (me:Person)-[conn:CONNECTED_WITH]-(friend:Person)
            WHERE elementId(me) = $personId AND elementId(friend) <> $personId
            MATCH (friend)-[posted:POSTED]->(post:Post)
            RETURN DISTINCT post, posted, friend
            ORDER BY post.createdAt DESC
            SKIP $skip LIMIT $limit
            """,
            countQuery = """
                MATCH (me:Person)-[:CONNECTED_WITH]-(friend:Person)
                WHERE elementId(me) = $personId AND elementId(friend) <> $personId
                MATCH (friend)-[:POSTED]->(post:Post)
                RETURN count(DISTINCT post)
            """)
    Page<Post> findNetworkFeed(@Param("personId") String personId, Pageable pageable);

    @Query(value = """
            MATCH (a:Person)-[rel:POSTED]->(p:Post)
            WHERE elementId(a) = $authorId
            RETURN p, rel, a
            ORDER BY p.createdAt DESC
            SKIP $skip LIMIT $limit
            """, countQuery = """
                MATCH (a:Person)-[:POSTED]->(p:Post)
                WHERE elementId(a) = $authorId
                RETURN count(p)
            """)
    Page<Post> findAllByAuthorId(@Param("authorId") String authorId, Pageable pageable);

}