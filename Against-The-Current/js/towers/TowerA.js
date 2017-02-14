class TowerA {
    constructor() {
        this.map = Dakra.map;
        this.frameMain = 'tower-1.png';
        this.frameError = 'tower-1-error.png';

        this.sprite = Dakra.towerGroup.create(
            (this.map.width + 1.5) * Dakra.configs.UNIT,
            100 + 0 * (Dakra.configs.UNIT + 20), 'towers', this.frameMain);

        this.sprite.anchor.setTo(0.5, 0.5);

        this.sprite.inputEnabled = true;
        this.sprite.events.onInputDown.add(this.onInputDown, this);
        this.sprite.events.onInputUp.add(this.onInputUp, this);

        this.onDrag = false;
        this.onFire = false;

        this.RANGE = 5;
        this.SHOT_DELAY = 300;
        this.BULLET_SPEED = 1200;
    }

    onInputDown() {
        if (this.onFire) {

        } else {
            this.onDrag = true;
            this.oldPositionX = this.sprite.position.x;
            this.oldPositionY = this.sprite.position.y;
        }
    }

    onInputUp() {
        if (this.onFire) {

        } else {
            this.onDrag = false;
            var x = Math.floor(this.sprite.position.x / Dakra.configs.UNIT);
            var y = Math.floor(this.sprite.position.y / Dakra.configs.UNIT);
            if (this.map.arrayMap[x][y] == 0) {
                this.sprite.position.x = (x + 0.5) * Dakra.configs.UNIT;
                this.sprite.position.y = (y + 0.5) * Dakra.configs.UNIT;
                this.onFire = true;
                this.map.arrayMap[x][y] = 1;
                Dakra.towers.push(new TowerA(this.map));
            } else {
                this.sprite.position.x = this.oldPositionX;
                this.sprite.position.y = this.oldPositionY;
                this.sprite.frameName = this.frameMain;
            }

            if (this.placeHolder !== undefined) this.placeHolder.sprite.kill();
        }
    }

    goBullet() {
        if (this.lastBulletShotAt === undefined) this.lastBulletShotAt = 0;
        if (Dakra.game.time.now - this.lastBulletShotAt < this.SHOT_DELAY) return;
        this.lastBulletShotAt = Dakra.game.time.now;
        new BulletA(this);
    }

    checkTargetInRange(target) {
        if (target === undefined) return false;
        if (!target.sprite.alive) return false;
        var x = this.sprite.position.x - target.sprite.position.x;
        var y = this.sprite.position.y - target.sprite.position.y;
        var distance = Math.sqrt(x * x + y * y);
        return distance <= this.RANGE * Dakra.configs.UNIT;
    }

    goNextTarget() {
        for (var i = 0; i < Dakra.enemies.length; i++) {
            var targetOnCheck = Dakra.enemies[i];
            if (this.checkTargetInRange(targetOnCheck)) {
                this.target = targetOnCheck;
                return;
            }
        }
    }

    update() {
        if (this.onFire) {
            if (this.target !== undefined) {
                var targetAngle = Dakra.game.math.angleBetween(
                    this.sprite.position.x, this.sprite.position.y,
                    this.target.sprite.position.x, this.target.sprite.position.y
                );
                this.sprite.rotation = targetAngle;
            }
            if (this.checkTargetInRange(this.target))
                this.goBullet();
            else this.goNextTarget();

        } else if (this.onDrag) {
            this.sprite.position.copyFrom(Dakra.game.input.mousePointer);

            var x = Math.floor(this.sprite.position.x / Dakra.configs.UNIT);
            var y = Math.floor(this.sprite.position.y / Dakra.configs.UNIT);
            if (this.map.arrayMap[x][y] == 0) {
                this.sprite.frameName = this.frameMain;

                if (this.placeHolder !== undefined)
                    this.placeHolder.sprite.kill();
                this.placeHolder = new placeHolderA(x, y);
            } else {
                this.sprite.frameName = this.frameError;

                if (this.placeHolder !== undefined)
                    this.placeHolder.sprite.kill();
            }
        }
    }
}
