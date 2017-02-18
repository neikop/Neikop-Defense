class Explosion {
    constructor(x, y) {
        this.sprite = Dakra.bulletBounceGroup.create(x, y, 'holders', 'tower-1-upgrade-1.png');
        this.sprite.father = this;
        this.sprite.anchor.setTo(0.5, 0.5);
        // this.sprite.scale.setTo(15, 15);
    }

    update() {
        if (this.counter === undefined) this.counter = 0;
        this.counter++;
        if (this.counter == 2) {
            this.sprite.kill();
        }
    }
}
