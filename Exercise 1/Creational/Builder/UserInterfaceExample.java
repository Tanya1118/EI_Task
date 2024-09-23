// Component interface

import java.util.ArrayList;
import java.util.List;

interface Component {
    String render();
}

// Concrete classes for UI components
class Button implements Component {
    private String label;

    public Button(String label) {
        this.label = label;
    }

    @Override
    public String render() {
        return "Button: " + label;
    }
}

class TextField implements Component {
    private String placeholder;

    public TextField(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String render() {
        return "TextField: " + placeholder;
    }
}

class Label implements Component {
    private String text;

    public Label(String text) {
        this.text = text;
    }

    @Override
    public String render() {
        return "Label: " + text;
    }
}

// Builder class for the User Interface
class UserInterfaceBuilder {
    private String title;
    private List<Component> components;

    public UserInterfaceBuilder() {
        components = new ArrayList<>();
    }

    public UserInterfaceBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public UserInterfaceBuilder addButton(String label) {
        components.add(new Button(label));
        return this;
    }

    public UserInterfaceBuilder addTextField(String placeholder) {
        components.add(new TextField(placeholder));
        return this;
    }

    public UserInterfaceBuilder addLabel(String text) {
        components.add(new Label(text));
        return this;
    }

    public UserInterface build() {
        return new UserInterface(title, components);
    }
}

// Final Product class
class UserInterface {
    private String title;
    private List<Component> components;

    public UserInterface(String title, List<Component> components) {
        this.title = title;
        this.components = components;
    }

    public void render() {
        System.out.println("User Interface: " + title);
        for (Component component : components) {
            System.out.println(component.render());
        }
    }
}

// Client code
public class UserInterfaceExample {
    public static void main(String[] args) {
        UserInterfaceBuilder builder = new UserInterfaceBuilder();
        UserInterface ui = builder.setTitle("My Application")
                                  .addLabel("Welcome to the App")
                                  .addTextField("Enter your name")
                                  .addButton("Submit")
                                  .build();

        ui.render();
    }
}
