class Tower {
    constructor(numType) {
        this.TYPE = numType;
        this.frameMain = 'tower-' + this.TYPE + '.png';
        this.frameError = 'tower-' + this.TYPE + '-error.png';
        this.frameHolder = 'tower-' + this.TYPE + '-holder.png';
        this.frameUpgrade1 = 'tower-' + this.TYPE + '-upgrade-1.png';
        this.frameUpgrade2 = 'tower-' + this.TYPE + '-upgrade-2.png';
        this.frameUpgrade3 = 'tower-' + this.TYPE + '-upgrade-3.png';

        this.sprite = Dakra.towerGroup.create(
            (Dakra.MAP.width + 1.0) * Dakra.configs.UNIT,
            this.TYPE * (Dakra.configs.UNIT + 20), 'towers', this.frameMain);

        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.inputEnabled = true;
        this.sprite.events.onInputDown.add(this.onInputDown, this);
        this.sprite.events.onInputUp.add(this.onInputUp, this);
        this.onDrag = false;
        this.onFire = false;

        this.BULLET_SPEED = 1200;
        this.LEVEL = 1;
        this.RANGE = 3;

        this.goUpgradeTo1();
        this.initSingleton();
    }

    goUpgradeTo1() {
        // required
    }

    goUpgradeTo2() {
        // required
    }

    goUpgradeTo3() {
        // required
    }

    initSingleton() {
        this.placeHolder = Dakra.towerHolderGroup
            .create(0, 0, 'towers', this.frameHolder);
        this.placeHolder.anchor.setTo(0.5, 0.5);
        this.placeHolder.kill();

        this.upgradeHolder = Dakra.towerHolderGroup.create(0, 0, 'holders',
            'tower-' + this.TYPE + '-upgrade-' + this.LEVEL + '.png');
        this.upgradeHolder.anchor.setTo(0.5, 0.5);
        this.upgradeHolder.kill();

        this.buttonDestroy = Dakra.game.add.button(0, 0, 'button-destroy',
            this.onInputDownDestroy, this);
        this.buttonDestroy.anchor.setTo(0.5, 0.5);
        this.buttonDestroy.kill();

        this.buttonUpgrade = Dakra.game.add.button(0, 0, 'button-upgrade',
            this.onInputDownUpgrade, this);
        this.buttonUpgrade.anchor.setTo(0.5, 0.5);
        this.buttonUpgrade.kill();
    }

    onInputDown() {
        if (this.onFire) {
            Dakra.background.reset(0, 0);
            Dakra.background.events.onInputDown.add(this.onInputDownBackground, this);

            this.upgradeHolder.reset(this.sprite.position.x, this.sprite.position.y);
            this.upgradeHolder.frameName = 'tower-' + this.TYPE + '-upgrade-' + this.LEVEL + '.png';

            this.buttonDestroy.reset(this.sprite.position.x - 11, this.sprite.position.y + 32);
            if (this.LEVEL < 3)
                this.buttonUpgrade.reset(this.sprite.position.x + 11, this.sprite.position.y + 32);

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
            if (Dakra.MAP.arrayMap[y][x] == Dakra.configs.PLACE &
                Dakra.MONEY >= this.BUILDING_PRICE) {
                Dakra.MONEY = Dakra.MONEY - this.BUILDING_PRICE;

                this.sprite.position.x = (x + 0.5) * Dakra.configs.UNIT;
                this.sprite.position.y = (y + 0.5) * Dakra.configs.UNIT;
                this.onFire = true;
                Dakra.MAP.arrayMap[y][x] = 0;
                Dakra.towers.push(
                    this.TYPE == 1 ? new TowerA() :
                    this.TYPE == 2 ? new TowerB() :
                    this.TYPE == 3 ? new TowerC() :
                    this.TYPE == 4 ? new TowerD() : 0
                );
            } else {
                this.sprite.reset(this.oldPositionX, this.oldPositionY);
                this.sprite.frameName = this.frameMain;
            }

            if (this.placeHolder !== undefined) this.placeHolder.kill();
        }
    }

    onInputDownBackground() {
        Dakra.background.kill();
        this.upgradeHolder.kill();
        this.buttonDestroy.kill();
        this.buttonUpgrade.kill();
    }

    onInputDownDestroy() {
        Dakra.MONEY += 100;
        this.onInputDownBackground();
        Dakra.towerGroup.remove(this.sprite);
        this.sprite.kill();
        var x = Math.floor(this.sprite.position.x / Dakra.configs.UNIT);
        var y = Math.floor(this.sprite.position.y / Dakra.configs.UNIT);
        Dakra.MAP.arrayMap[y][x] = Dakra.configs.PLACE;
    }

    onInputDownUpgrade() {
        this.onInputDownBackground();
        if (Dakra.MONEY < this.UPGRADE_PRICE) return;
        if (this.LEVEL == 1) this.goUpgradeTo2();
        else if (this.LEVEL == 2) this.goUpgradeTo3();
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
            if (Dakra.MAP.arrayMap[y][x] == Dakra.configs.PLACE) {
                this.sprite.frameName = this.frameMain;
                this.placeHolder.reset(x * 40 + 20, y * 40 + 20);
            } else {
                this.sprite.frameName = this.frameError;
                if (this.placeHolder !== undefined) this.placeHolder.kill();
            }
        }
    }

    goBullet() {
        if (this.lastBulletShotAt === undefined) this.lastBulletShotAt = 0;
        if (Dakra.game.time.now - this.lastBulletShotAt < this.SHOT_DELAY) return;
        this.lastBulletShotAt = Dakra.game.time.now;
        if (this.TYPE == 3 & this.LEVEL >= 3) {
            Dakra.bulletBounceGroup.getFirstDead().father.revive(this, this.target, this, 1);
        } else Dakra.towerBulletGroup.getFirstDead().father.revive(this);
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

    checkTargetInRange(target) {
        if (target === undefined) return false;
        if (!target.sprite.alive) return false;
        var x = this.sprite.position.x - target.sprite.position.x;
        var y = this.sprite.position.y - target.sprite.position.y;
        var distance = Math.sqrt(x * x + y * y);
        return distance <= this.RANGE * Dakra.configs.UNIT;
    }

    stop() {
        this.placeHolder.kill();
        this.upgradeHolder.kill();
        this.buttonDestroy.kill();
        this.buttonUpgrade.kill();
    }
}
