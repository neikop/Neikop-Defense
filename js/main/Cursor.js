class Cursor {
    constructor() {
        this.sprite = Dakra.game.add.sprite(0, 0, 'cursors');
        this.sprite.anchor.setTo(0.5, 0.5);
        this.x = 0;
        this.y = 0;

        this.map = Dakra.map;
        this.updatePosition();
    }

    update() {
        if (Dakra.game.input.keyboard.isDown(Phaser.Keyboard.UP)) {
            if (this.countUp == 0) {
                this.countUp++;
                this.moveUp();
            }
        } else this.countUp = 0;

        if (Dakra.game.input.keyboard.isDown(Phaser.Keyboard.DOWN)) {
            if (this.countDown == 0) {
                this.countDown++;
                this.moveDown();
            }
        } else this.countDown = 0;

        if (Dakra.game.input.keyboard.isDown(Phaser.Keyboard.LEFT)) {
            if (this.countLeft == 0) {
                this.countLeft++;
                this.moveLeft();
            }
        } else this.countLeft = 0;

        if (Dakra.game.input.keyboard.isDown(Phaser.Keyboard.RIGHT)) {
            if (this.countRight == 0) {
                this.countRight++;
                this.moveRight();
            }
        } else this.countRight = 0;
    }

    moveTo(x, y) {
        this.x = x;
        this.y = y;
        this.updatePosition();
    }

    moveUp() {
        if (this.y == 0) return;
        this.y = this.y - 1;
        this.updatePosition();
    }

    moveDown() {
        if (this.y == this.map.height - 1) return;
        this.y = this.y + 1;
        this.updatePosition();
    }

    moveLeft() {
        if (this.x == 0) return;
        this.x = this.x - 1;
        this.updatePosition();
    }

    moveRight() {
        if (this.x == this.map.width - 1) return;
        this.x = this.x + 1;
        this.updatePosition();
    }

    updatePosition() {
        this.sprite.position.y = (this.y + 0.5) * Dakra.configs.UNIT;
        this.sprite.position.x = (this.x + 0.5) * Dakra.configs.UNIT;
        if (this.map.arrayMap[this.x][this.y] == 0)
            this.sprite.frameName = 'cursor-green.png';
        else this.sprite.frameName = 'cursor-red.png';
    }
}
