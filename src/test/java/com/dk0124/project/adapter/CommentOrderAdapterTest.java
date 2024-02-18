package com.dk0124.project.adapter;

import com.dk0124.project.comment.application.adapter.CommentOrderAdapter;
import com.dk0124.project.comment.exception.InvalidCommentIdException;
import com.dk0124.project.comment.infrastructure.TechArticleCommentEntity;
import com.dk0124.project.comment.infrastructure.repository.TechArticleCommentEntityRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@ActiveProfiles("local")
public class CommentOrderAdapterTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TechArticleCommentEntityRepository commentRepository;

    private CommentOrderAdapter commentOrderAdapter;

    private final Long articleId = 1L;

    //comment A;
    private final Long a_commentOrder = 1L;
    private final Long a_level = 0L;
    private final Long a_sort_number = 0L;
    private final Long a_child_count = 4L;
    private Long a_id;

    // comment B;
    private final Long b_commentOrder = 1L;
    private final Long b_level = 1L;
    private final Long b_sort_number = 1L;
    private final Long b_child_count = 1L;
    private Long b_id;

    // comment C;
    private final Long c_commentOrder = 1L;
    private final Long c_level = 2L;
    private final Long c_sort_number = 2L;
    private final Long c_child_count = 0L;
    private Long c_id;


    // comment D;
    private final Long d_commentOrder = 1L;
    private final Long d_level = 1L;
    private final Long d_sort_number = 3L;
    private final Long d_child_count = 1L;
    private Long d_id;

    // comment e;
    private final Long e_commentOrder = 1L;
    private final Long e_level = 2L;
    private final Long e_sort_number = 4L;
    private final Long e_child_count = 0L;
    private Long e_id;

    static final FixtureMonkey monkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .build();

    /***
     * 댓글 순서 .
     * A
     *      B
     *          C
     *      D
     *          E
     * ***/


    @BeforeEach
    void initialize() {
        if (commentOrderAdapter == null)
            this.commentOrderAdapter = new CommentOrderAdapter(commentRepository);
    }

    @AfterEach
    void deleteAll() {
        commentRepository.deleteAll();
    }


    @Test
    void commentOrder_생성_성공_다음_번호_생성() {
        readyComment();
        assertEquals(a_commentOrder + 1, commentOrderAdapter.generateNextCommentOrder(articleId));
    }

    @Test
    void commentOrder_생성_성공_첫_댓글_번호_생성() {
        assertEquals(0, commentOrderAdapter.generateNextCommentOrder(articleId));
    }

    @Test
    void commentOrder_생성_성공_두번째_댓글_번호_생성() {
        readyComment();
        assertEquals(a_commentOrder + 1, commentOrderAdapter.generateNextCommentOrder(articleId));
    }

    @Test
    void 각_댓글_다음_sortNumber_채번() {
        readyComment();
        assertEquals(5L, commentOrderAdapter.generateNextSortNumber(articleId,a_id));
        assertEquals(3L, commentOrderAdapter.generateNextSortNumber(articleId,b_id));
        assertEquals(3L, commentOrderAdapter.generateNextSortNumber(articleId,c_id));
        assertEquals(5L, commentOrderAdapter.generateNextSortNumber(articleId,d_id));
        assertEquals(5L, commentOrderAdapter.generateNextSortNumber(articleId,e_id));
    }
    
    @Test
    void 각_childCount_증가_확인() {
        readyComment();
        commentOrderAdapter.updateChildCountOfSucceedingParents(e_id);
        entityManager.clear(); /** 중간에 jpql 써서 다시 조회 해야함 **/
        assertEquals(a_child_count+1, commentRepository.findById(a_id).get().getChildCount());
        assertEquals(d_child_count+1, commentRepository.findById(d_id).get().getChildCount());
        assertEquals(e_child_count+1, commentRepository.findById(e_id).get().getChildCount());
    }

    @Test
    void updatePrecedingSortNumber_업데이트_여부_확인_B_위치_sortNumber_증가(){
        readyComment();
        commentOrderAdapter.updatePrecedingSortNumber(articleId, a_commentOrder,1L);
        entityManager.clear();

        assertEquals(b_sort_number+1, commentRepository.findById(b_id).get().getSortNumber());
        assertEquals(c_sort_number+1, commentRepository.findById(c_id).get().getSortNumber());
        assertEquals(d_sort_number+1, commentRepository.findById(d_id).get().getSortNumber());
        assertEquals(e_sort_number+1, commentRepository.findById(e_id).get().getSortNumber());
    }


    @Test
    void 없는_댓글의_sortNumber_생성_예외 () {
        assertThrows(InvalidCommentIdException.class, () -> commentOrderAdapter.generateNextSortNumber(articleId, -10L));
    }

    void readyComment() {
        var a = commentRepository.save(monkey.giveMeBuilder(TechArticleCommentEntity.class)
                .set("commentId", null)
                .set("articleId", articleId)
                .set("content", "A")
                .set("deleted", Boolean.FALSE)
                .set("commentOrder", a_commentOrder)
                .set("level", a_level)
                .set("sortNumber", a_sort_number)
                .set("childCount", a_child_count)
                .set("parentId",null)
                .sample());
        a_id = a.getCommentId();

        var b = commentRepository.save(monkey.giveMeBuilder(TechArticleCommentEntity.class)
                .set("commentId", null)
                .set("articleId", articleId)
                .set("content", "B")
                .set("deleted", Boolean.FALSE)
                .set("commentOrder", b_commentOrder)
                .set("level", b_level)
                .set("sortNumber", b_sort_number)
                .set("childCount", b_child_count)
                .set("parentId", a_id)
                .sample());
        b_id = b.getCommentId();

        var c = commentRepository.save(monkey.giveMeBuilder(TechArticleCommentEntity.class)
                .set("commentId", null)
                .set("articleId", articleId)
                .set("content", "C")
                .set("deleted", Boolean.FALSE)
                .set("commentOrder", c_commentOrder)
                .set("level", c_level)
                .set("sortNumber", c_sort_number)
                .set("childCount", c_child_count)
                .set("parentId", b_id)
                .sample());

        c_id = c.getCommentId();

        var d = commentRepository.save(monkey.giveMeBuilder(TechArticleCommentEntity.class)
                .set("commentId", null)
                .set("articleId", articleId)
                .set("content", "D")
                .set("deleted", Boolean.FALSE)
                .set("commentOrder", d_commentOrder)
                .set("level", d_level)
                .set("sortNumber", d_sort_number)
                .set("childCount", d_child_count)
                .set("parentId", a_id)
                .sample());
        d_id = d.getCommentId();

        var e = commentRepository.save(monkey.giveMeBuilder(TechArticleCommentEntity.class)
                .set("commentId", null)
                .set("articleId", articleId)
                .set("content", "E")
                .set("deleted", Boolean.FALSE)
                .set("commentOrder", e_commentOrder)
                .set("level", e_level)
                .set("sortNumber", e_sort_number)
                .set("childCount", e_child_count)
                .set("parentId", d_id)
                .sample());
        e_id = e.getCommentId();
    }

}
