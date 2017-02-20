class Enemy {
    constructor(numType, coin, armor, health, speed) {
        this.arrayPoint = Dakra.MAP.arrayPoint[
            Math.floor(Math.random() * Dakra.MAP.arrayPoint.length)
        ];
        this.currPos = 0;
        this.nextPos = 1;
        this.sprite = Dakra.enemyGroup.create(
            (this.arrayPoint[this.currPos].x + 0.5) * Dakra.configs.UNIT,
            (this.arrayPoint[this.currPos].y + 0.5) * Dakra.configs.UNIT,
            'chars'
        );
        this.mainFrame = 'char-' + numType + '-godown-2.png';
        this.TYPE = numType;
        this.sprite.father = this;
        this.sprite.anchor.setTo(0.5, 0.7);
        this.sprite.inputEnabled = true;
        this.sprite.events.onInputDown.add(this.onInputDown, this);
        this.setupAnimation(numType);

        this.COIN = coin + 0 * Math.floor(Dakra.enemyGroup.length / 10);
        this.armor = armor + 20 * Math.floor(Dakra.enemyGroup.length / 10);
        this.sprite.health = health + 200 * Math.floor(Dakra.enemyGroup.length / 10);
        this.movementSpeed = speed; // MAX = 300
    }

    onInputDown() {
        Dakra.enemyInfo.revive(this);
    }

    beShot(bullet) {
        this.movementSpeed = this.movementSpeed - bullet.slow;
        if (this.movementSpeed < 20) this.movementSpeed = 20;

        this.sprite.damage(bullet.damage * 100 / (100 + this.armor * bullet.armorPen));
        if (!this.sprite.alive) {
            Dakra.MONEY = Dakra.MONEY + this.COIN;
            Dakra.KILL++;
        }
    }

    setupAnimation(numType) {
        this.sprite.animations.add('godown', Phaser.Animation
            .generateFrameNames('char-' + numType + '-godown-', 1, 3, '.png', 1), 15, true);
        this.sprite.animations.add('goleft', Phaser.Animation
            .generateFrameNames('char-' + numType + '-goleft-', 1, 3, '.png', 1), 15, true);
        this.sprite.animations.add('goright', Phaser.Animation
            .generateFrameNames('char-' + numType + '-goright-', 1, 3, '.png', 1), 15, true);
        this.sprite.animations.add('goup', Phaser.Animation
            .generateFrameNames('char-' + numType + '-goup-', 1, 3, '.png', 1), 15, true);
    }

    update() {
        if (this.nextPos == this.arrayPoint.length) {
            Dakra.LIFE--;
            this.sprite.kill();
        } else this.checkDirection();
    }

    checkDirection() {
        var currPosition = new Phaser.Point(
            this.sprite.position.x,
            this.sprite.position.y
        );
        var nextPosition = new Phaser.Point(
            (this.arrayPoint[this.nextPos].x + 0.5) * Dakra.configs.UNIT,
            (this.arrayPoint[this.nextPos].y + 0.5) * Dakra.configs.UNIT
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
            this.sprite.position.x = (this.arrayPoint[this.currPos].x + 0.5) * Dakra.configs.UNIT;
            this.sprite.position.y = (this.arrayPoint[this.currPos].y + 0.5) * Dakra.configs.UNIT;
        }
    }

    goUp() {
        this.sprite.body.velocity.x = 0;
        this.sprite.body.velocity.y = -this.movementSpeed;
        this.sprite.animations.play('goup');
    }

    goDown() {
        this.sprite.body.velocity.x = 0;
        this.sprite.body.velocity.y = this.movementSpeed;
        this.sprite.animations.play('godown');
    }

    goRight() {
        this.sprite.body.velocity.x = this.movementSpeed;
        this.sprite.body.velocity.y = 0;
        this.sprite.animations.play('goright');
    }

    goLeft() {
        this.sprite.body.velocity.x = -this.movementSpeed;
        this.sprite.body.velocity.y = 0;
        this.sprite.animations.play('goleft');
    }

    stop() {
        this.sprite.body.velocity.x = 0;
        this.sprite.body.velocity.y = 0;
        this.sprite.animations.stop();
    }

    checkApproach(x, y) {
        return Math.abs(x - y) < 1;
    }
}
