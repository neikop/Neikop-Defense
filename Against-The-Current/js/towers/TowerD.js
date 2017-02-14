class TowerD {
    constructor() {
        this.map = Dakra.map;

        this.sprite = Dakra.game.add.sprite(60 + this.map.width * 40, 250, 'towers', 'tower-4.png');
        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.inputEnabled = true;
        this.onDrag = false;
        this.onGone = false;

        this.sprite.events.onInputDown.add(this.onInputDown, this);
        this.sprite.events.onInputUp.add(this.onInputUp, this);
    }

    onInputDown() {
        if (this.onGone) {

        } else {
            this.onDrag = true;
            this.oldPositionX = this.sprite.position.x;
            this.oldPositionY = this.sprite.position.y;
        }
    }

    onInputUp() {
        if (this.onGone) {

        } else {
            this.onDrag = false;
            var x = Math.floor(this.sprite.position.x / 40);
            var y = Math.floor(this.sprite.position.y / 40);
            if (this.map.arrayMap[x][y] == 0) {
                this.sprite.position.x = (x + 0.5) * 40;
                this.sprite.position.y = (y + 0.5) * 40;
                this.onGone = true;
                this.map.arrayMap[x][y] = 1;
                Dakra.towers.push(new TowerD(this.map));
            } else {
                this.sprite.position.x = this.oldPositionX;
                this.sprite.position.y = this.oldPositionY;
                this.sprite.frameName = 'tower-4.png';
            }
        }
    }

    update() {
        if (this.onGone) {

        } else if (this.onDrag) {
            this.sprite.position.copyFrom(Dakra.game.input.mousePointer);

            var x = Math.floor(this.sprite.position.x / 40);
            var y = Math.floor(this.sprite.position.y / 40);
            if (this.map.arrayMap[x][y] == 0)
                this.sprite.frameName = 'tower-4.png';
            else this.sprite.frameName = 'tower.png';
        }
    }
}
