class placeHolderA {
    constructor(x, y) {
        this.sprite = Dakra.towerHolderGroup.create(x * 40 + 20, y * 40 + 20, 'holders', 'tower-1-holder.png');
        this.sprite.anchor.setTo(0.5, 0.5);
    }
}
