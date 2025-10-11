package com.company.onboarding.view.step;

import com.company.onboarding.entity.Step;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "steps", layout = MainView.class)
@ViewController(id = "Step.list")
@ViewDescriptor(path = "step-list-view.xml")
@LookupComponent("stepsDataGrid")
@DialogMode(width = "50em", height = "37.5em")
public class StepListView extends StandardListView<Step> {
}