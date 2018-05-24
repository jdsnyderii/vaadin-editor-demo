package com.jim.editor;

import com.jim.data.Person;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import org.apache.commons.text.WordUtils;
import org.vaadin.addons.searchbox.SearchBox;

import javax.servlet.annotation.WebServlet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SpringUI
@Theme("mytheme")
public class EditorUI extends UI {

  @Override
  protected void init(VaadinRequest request) {
    // The root of the component hierarchy
    VerticalLayout content = new VerticalLayout();
    content.setSizeFull(); // Use entire window
    setContent(content);   // Attach to the UI

    SearchBox searchBox5 = new SearchBox("Search", SearchBox.ButtonPosition.RIGHT);
    searchBox5.setCaption("Search suggestions");
    searchBox5.setSuggestionGenerator(this::suggestUsers, this::convertValueUser, this::convertCaptionUser);
    searchBox5.setWidth("350px");
    searchBox5.setSearchMode(SearchBox.SearchMode.DEBOUNCE);
    searchBox5.setDebounceTime(200); // event fires 200 ms after typing
    content.addComponent(searchBox5);

    // Add some component
    content.addComponent(new Label("<b>Hello!</b> - How are you?",
        ContentMode.HTML));

    Grid<Person> grid = new Grid<>();
    grid.setCaption("My Grid");
    grid.setItems(GridExample.generateContent());
    grid.addColumn(Person::getName).setCaption("Name");
    grid.addColumn(Person::getDob).setCaption("Date of birth");
    grid.addColumn(Person::getLink, new ComponentRenderer()).setCaption("LinkedIn");

    grid.setSizeFull();

    grid.setSelectionMode(Grid.SelectionMode.MULTI);

    grid.addSelectionListener(event -> {
      Set<Person> selected = event.getAllSelectedItems();
      Notification.show(selected.size() + " items selected");
    });

    content.addComponent(grid);
    content.setExpandRatio(grid, 1); // Expand to fill
  }

  private List<Person> suggestUsers(String query, int cap) {
    return GridExample.generateContent().stream()
        .filter(user -> user.getName().toLowerCase().contains(query.toLowerCase()))
        .limit(cap).collect(Collectors.toList());
  }

  private String convertValueUser(Person user) {
    return WordUtils.capitalizeFully(user.getName(), ' ');
  }

  private String convertCaptionUser(Person user, String query) {
    return "<div class='suggestion-container'>"
        + "<img src='" + user.getPicture() + "' class='userimage'>"
        + "<span class='username'>"
        + user.getName().replaceAll("(?i)(" + query + ")", "<b>$1</b>")
        + "</span>"
        + "</div>";
  }

  @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
  @VaadinServletConfiguration(ui = EditorUI.class, productionMode = false)
  public static class MyUIServlet extends VaadinServlet {
  }
}
