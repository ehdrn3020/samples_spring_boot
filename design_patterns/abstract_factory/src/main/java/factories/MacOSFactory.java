package factories;
import buttons.Button;
import checkboxes.Checkbox;

public class MacOSFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new buttons.MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new checkboxes.MacOSCheckbox();
    }
}
