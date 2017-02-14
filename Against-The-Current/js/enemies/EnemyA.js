class EnemyA {
    constructor() {
        this.map = Dakra.map;
        this.currPos = 0;
        this.nextPos = 1;
        this.sprite = Dakra.enemyGroup.create(
            (this.map.arrayPoint[this.currPos].x + 0.5) * Dakra.configs.UNIT,
            (this.map.arrayPoint[this.currPos].y + 0.5) * Dakra.configs.UNIT,
            'cursors', 'cursor-red.png'
        );
        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.health = 100;

        this.movementSpeed = 50; // MAX = 300
    }

    update() {
        if (this.nextPos == this.map.arrayPoint.length) {
            this.sprite.body.velocity.x = 0;
            this.sprite.body.velocity.y = 0;
            this.sprite.position.x = (this.map.arrayPoint[this.currPos].x + 0.5) * Dakra.configs.UNIT;
            this.sprite.position.y = (this.map.arrayPoint[this.currPos].y + 0.5) * Dakra.configs.UNIT;

            Dakra.countDead++;
            Dakra.enemies.shift();
            this.sprite.kill();
        } else this.checkDirection();
    }

    checkDirection() {
        var currPosition = new Phaser.Point(
            this.sprite.position.x,
            this.sprite.position.y
        );
        var nextPosition = new Phaser.Point(
            (this.map.arrayPoint[this.nextPos].x + 0.5) * Dakra.configs.UNIT,
            (this.map.arrayPoint[this.nextPos].y + 0.5) * Dakra.configs.UNIT
        );

        if (this.checkApproach(nextPosition.x, currPosition.x) &
            this.checkApproach(nextPosition.y, currPosition.y)) {
            this.sprite.body.velocity.x = 0;
            this.sprite.body.velocity.y = 0;
            this.currPos = this.nextPos;
            this.nextPos = this.currPos + 1;
        } else if (nextPosition.x == currPosition.x) {
            if (nextPosition.y > currPosition.y) this.goDown();
            if (nextPosition.y < currPosition.y) this.goUp();
        } else if (nextPosition.y == currPosition.y) {
            if (nextPosition.x > currPosition.x) this.goRight();
            if (nextPosition.x < currPosition.x) this.goLeft();
        } else {
            this.sprite.position.x = (this.map.arrayPoint[this.currPos].x + 0.5) * Dakra.configs.UNIT;
            this.sprite.position.y = (this.map.arrayPoint[this.currPos].y + 0.5) * Dakra.configs.UNIT;
        }
    }

    goUp() {
        this.sprite.body.velocity.x = 0;
        this.sprite.body.velocity.y = -this.movementSpeed;
    }

    goDown() {
        this.sprite.body.velocity.x = 0;
        this.sprite.body.velocity.y = this.movementSpeed;
    }

    goRight() {
        this.sprite.body.velocity.x = this.movementSpeed;
        this.sprite.body.velocity.y = 0;
    }

    goLeft() {
        this.sprite.body.velocity.x = -this.movementSpeed;
        this.sprite.body.velocity.y = 0;
    }

    checkApproach(x, y) {
        var z = Math.abs(x - y);
        return z < 0.000001;
    }
}
