package factories;
import buttons.Button;
import checkboxes.Checkbox;

public class WindowsFactory implements GUIFactory {
    @Override
    public buttons.Button createButton() {
        return new buttons.WindowsButton();
    }

    @Override
    public checkboxes.Checkbox createCheckbox() {
        return new checkboxes.WindowsCheckbox();
    }
}
