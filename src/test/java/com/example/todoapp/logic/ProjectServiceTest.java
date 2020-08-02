package com.example.todoapp.logic;

import com.example.todoapp.TaskConfigurationProperties;
import com.example.todoapp.model.Project;
import com.example.todoapp.model.ProjectRepository;
import com.example.todoapp.model.TaskGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {


        @Test
        @DisplayName("should throw IllegalStateException when configured to allow just 1  group and the other undone groups")
        void createGroup_noMultipleGroupsConfig_And_undoneGroupExist_throwsIllegalStateException() {
            //given
            TaskGroupRepository mockGroupRepository = groupRepositoryReturning(true);

            TaskConfigurationProperties mockConfig = configurationReturning(false);

            ProjectService toTest = new ProjectService(null, mockGroupRepository, null, mockConfig);
            //when      //then

            Throwable exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
            assertThat(exception)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("one undone group");

            /*
            assertThatIllegalStateException()
                    .isThrownBy(() -> {
                        toTest.createGroup(LocalDateTime.now(), 0);
                    });
              /*
            assertThatThrownBy(() -> {
                toTest.createGroup(LocalDateTime.now(), 0);
            }).isInstanceOf(IllegalStateException.class);
            /*
            */
        }




    @Test
    @DisplayName("should throw IllegalArgumentException when configuration ok and no projects fo given id")
    void createGroup_configurationOk_And_noProjects_throwsIllegalArgumentException() {
        //given
        ProjectRepository mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        TaskConfigurationProperties mockConfig = configurationReturning(true);

        ProjectService toTest = new ProjectService(mockRepository, null, null, mockConfig);
        //when
        //then

        Throwable exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not found");
    }




    @Test
    @DisplayName("should throw IllegalArgumentException when configuration allow just 1 group and projects projects fo given id")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExist_throwsIllegalArgumentException() {
        //given
        ProjectRepository mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());

        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);

        TaskConfigurationProperties mockConfig = configurationReturning(false);

        ProjectService toTest = new ProjectService(mockRepository, mockGroupRepository, null, mockConfig);
        //when
        //then

        Throwable exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));

        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("not found");
    }



    private TaskGroupRepository groupRepositoryReturning(final boolean result) {
        TaskGroupRepository mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndTasks_Group_ID(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }

    private TaskConfigurationProperties configurationReturning(final boolean result) {
            TaskConfigurationProperties.Template mockTemplate = mock(TaskConfigurationProperties.Template.class);
            when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);

            TaskConfigurationProperties mockConfig = mock(TaskConfigurationProperties.class);
            when(mockConfig.getTemplate()).thenReturn(mockTemplate);
            return mockConfig;
    }
}

