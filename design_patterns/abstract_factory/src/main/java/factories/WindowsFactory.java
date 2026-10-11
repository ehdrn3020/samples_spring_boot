package factories;
import buttons.Button;
import checkboxes.Checkbox;

public class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new buttons.WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new checkboxes.WindowsCheckbox();
    }
}
