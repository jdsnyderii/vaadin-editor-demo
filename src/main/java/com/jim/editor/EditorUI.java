package com.jim.editor;

import com.jim.data.Person;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

import java.util.Set;


@SpringUI
public class EditorUI extends UI {

  @Override
  protected void init(VaadinRequest request) {
    // The root of the component hierarchy
    VerticalLayout content = new VerticalLayout();
    content.setSizeFull(); // Use entire window
    setContent(content);   // Attach to the UI

    // Add some component
    content.addComponent(new Label("<b>Hello!</b> - How are you?",
        ContentMode.HTML));

    Grid<Person> grid = new Grid<>();
    grid.setCaption("My Grid");
    grid.setItems(GridExample.generateContent());
    grid.addColumn(Person::getName).setCaption("Name");
    grid.addColumn(Person::getDob).setCaption("Date of birth");
    grid.setSizeFull();

    grid.setSelectionMode(Grid.SelectionMode.MULTI);

    grid.addSelectionListener(event -> {
      Set<Person> selected = event.getAllSelectedItems();
      Notification.show(selected.size() + " items selected");
    });

    content.addComponent(grid);
    content.setExpandRatio(grid, 1); // Expand to fill
  }
}
