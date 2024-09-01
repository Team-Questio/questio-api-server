package team_questio.questio.portfolio.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team_questio.questio.portfolio.application.dto.QuestDetailInfo;
import team_questio.questio.portfolio.domain.Feedback;
import team_questio.questio.portfolio.domain.Quest;
import team_questio.questio.portfolio.persistence.QuestRepository;

@ExtendWith(MockitoExtension.class)
class QuestServiceTest {
    @Mock
    private QuestRepository questRepository;
    @InjectMocks
    private QuestService questService;

    @Test
    @DisplayName("[Question 테스트] 포트폴리오 질문 반환 형식 테스트")
    void getQuestionTest() {
        //given
        final List<Quest> quests = List.of(
                new Quest("질문1", "답변1", 1L)
        );
        final Long portfolioId = 1L;
        given(questRepository.findByPortfolioId(any()))
                .willReturn(quests);

        final var expected = new QuestDetailInfo(null, "질문1", "답변1", Feedback.NONE);
        
        //when
        var result = questService.getQuests(portfolioId);

        //then
        assertThat(result.get(0).question()).isEqualTo(expected.question());
        assertThat(result.get(0).answer()).isEqualTo(expected.answer());
        assertThat(result.get(0).feedback()).isEqualTo(expected.feedback());
    }
}