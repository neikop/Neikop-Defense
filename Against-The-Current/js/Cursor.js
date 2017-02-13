class Cursor {
    constructor(x, y, configs) {
        this.sprite = Dakra.game.add.sprite(
            x * 40,
            y * 40,
            'cursor-red'
        );
        this.x = x;
        this.y = y;
        this.configs = configs;
    }

    update() {
        if (Dakra.keyboard.isDown(Phaser.Keyboard.UP)) {
            if (this.countUp == 0) {
                this.countUp++;
                this.moveUp();
            }
        } else this.countUp = 0;

        if (Dakra.keyboard.isDown(Phaser.Keyboard.DOWN)) {
            if (this.countDown == 0) {
                this.countDown++;
                this.moveDown();
            }
        } else this.countDown = 0;

        if (Dakra.keyboard.isDown(Phaser.Keyboard.LEFT)) {
            if (this.countLeft == 0) {
                this.countLeft++;
                this.moveLeft();
            }
        } else this.countLeft = 0;

        if (Dakra.keyboard.isDown(Phaser.Keyboard.RIGHT)) {
            if (this.countRight == 0) {
                this.countRight++;
                this.moveRight();
            }
        } else this.countRight = 0;
    }

    moveUp() {
        if (this.y == 0) return;
        this.y = this.y - 1;
        this.updatePosition();
    }

    moveDown() {
        if (this.y == Dakra.configs.screenHeight / 40 - 1) return;
        this.y = this.y + 1;
        this.updatePosition();
    }

    moveLeft() {
        if (this.x == 0) return;
        this.x = this.x - 1;
        this.updatePosition();
    }

    moveRight() {
        if (this.x == Dakra.configs.screenWidth / 40 - 1) return;
        this.x = this.x + 1;
        this.updatePosition();
    }

    updatePosition() {
        this.sprite.position.y = this.y * 40;
        this.sprite.position.x = this.x * 40;
        if (Dakra.arrayMap[this.x][this.y] == 0) this.sprite.frameName = 'cursor-green';
        else this.sprite.frameName = 'cursor-red';
    }
}
