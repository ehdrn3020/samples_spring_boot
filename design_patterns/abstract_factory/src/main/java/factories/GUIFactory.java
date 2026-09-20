package factories;
import buttons.Button;
import checkboxes.Checkbox;

public interface GUIFactory {
    Button createButton(); // public abstract Button createButton();
    Checkbox createCheckbox(); // public abstract Checkbox createCheckbox();
}
