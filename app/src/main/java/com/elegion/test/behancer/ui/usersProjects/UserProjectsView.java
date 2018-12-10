package com.elegion.test.behancer.ui.usersProjects;

import com.elegion.test.behancer.common.BaseView;
import com.elegion.test.behancer.data.model.project.Project;

import java.util.List;

public interface UserProjectsView extends BaseView {

    void showProjects(List<Project> projects);
}
