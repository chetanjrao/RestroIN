package in.restroin.restroin.utils;

import in.restroin.restroin.models.Item;

public class SelectableItem extends Item {
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;
    public SelectableItem(String date, boolean isChecked) {
        super(date);
        this.isChecked = isChecked;
    }
}
