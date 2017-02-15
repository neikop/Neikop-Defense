class TowerC {
    constructor() {
        this.TYPE = 3;
        this.frameMain = 'tower-' + this.TYPE + '.png';
        this.frameError = 'tower-' + this.TYPE + '-error.png';
        this.frameHolder = 'tower-' + this.TYPE + '-holder.png';
        this.frameUpgrade1 = 'tower-' + this.TYPE + '-upgrade-1.png';
        this.frameUpgrade2 = 'tower-' + this.TYPE + '-upgrade-2.png';
        this.frameUpgrade3 = 'tower-' + this.TYPE + '-upgrade-3.png';

        this.map = Dakra.map;
        this.sprite = Dakra.towerGroup.create(
            (this.map.width + 1.5) * Dakra.configs.UNIT,
            this.TYPE * (Dakra.configs.UNIT + 20), 'towers', this.frameMain);

        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.inputEnabled = true;
        this.sprite.events.onInputDown.add(this.onInputDown, this);
        this.sprite.events.onInputUp.add(this.onInputUp, this);
        this.onDrag = false;
        this.onFire = false;

        this.RANGE = 3;
        this.DAMAGE = 50;
        this.SLOW = 0;

        this.ARMOR_PEN = 0;
        this.SHOT_DELAY = 800;
        this.BULLET_SPEED = 800;

        this.LEVEL = 1;
    }

    goUpgradeTo2() {
        this.LEVEL = 2;
        this.RANGE = 4;
        this.DAMAGE = 70;
        this.SLOW = 0;
    }

    goUpgradeTo3() {
        this.LEVEL = 3;
        this.RANGE = 5;
        this.DAMAGE = 100;
        this.SLOW = 0;
    }

    onInputDown() {
        if (this.onFire) {
            if (Dakra.background === undefined) {
                this.upgradeHolder = Dakra.towerHolderGroup
                    .create(this.sprite.position.x, this.sprite.position.y, 'holders',
                        'tower-' + this.TYPE + '-upgrade-' + this.LEVEL + '.png');
                this.upgradeHolder.anchor.setTo(0.5, 0.5);

                Dakra.background = Dakra.backGroup.create(0, 0, 'background-hided');
                Dakra.background.inputEnabled = true;
                Dakra.background.events.onInputDown.add(this.onInputDownBackground, this);

                if (this.buttonDestroy === undefined) {
                    this.buttonDestroy = Dakra.game.add.button(
                        this.sprite.position.x - 11, this.sprite.position.y + 32, 'button-destroy',
                        this.onInputDownDestroy, this);
                    this.buttonDestroy.anchor.setTo(0.5, 0.5);
                }
                if (this.buttonUpgrade === undefined) {
                    this.buttonUpgrade = Dakra.game.add.button(
                        this.sprite.position.x + 11, this.sprite.position.y + 32, 'button-upgrade',
                        this.onInputDownUpgrade, this);
                    this.buttonUpgrade.anchor.setTo(0.5, 0.5);
                }
            }

        } else {
            this.onDrag = true;
            this.oldPositionX = this.sprite.position.x;
            this.oldPositionY = this.sprite.position.y;
        }
    }

    onInputDownBackground() {
        Dakra.towerHolderGroup.remove(this.upgradeHolder);
        this.upgradeHolder.kill();
        Dakra.backGroup.remove(Dakra.background);
        Dakra.background.kill();
        Dakra.background = undefined;

        if (this.buttonDestroy !== undefined) {
            this.buttonDestroy.kill();
            this.buttonDestroy = undefined;
        }
        if (this.buttonUpgrade !== undefined) {
            this.buttonUpgrade.kill();
            this.buttonUpgrade = undefined;
        }
    }

    onInputDownDestroy() {
        this.onInputDownBackground();
        Dakra.towerGroup.remove(this.sprite);
        this.sprite.kill();
        var x = Math.floor(this.sprite.position.x / Dakra.configs.UNIT);
        var y = Math.floor(this.sprite.position.y / Dakra.configs.UNIT);
        this.map.arrayMap[x][y] = 0;
    }

    onInputDownUpgrade() {
        this.onInputDownBackground();
        if (this.LEVEL == 1) this.goUpgradeTo2();
        else if (this.LEVEL == 2) this.goUpgradeTo3();
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
                Dakra.towers.push(
                    this.TYPE == 1 ? new TowerA(this.map) :
                    this.TYPE == 2 ? new TowerB(this.map) :
                    this.TYPE == 3 ? new TowerC(this.map) :
                    this.TYPE == 4 ? new TowerD(this.map) : 0);
            } else {
                this.sprite.position.x = this.oldPositionX;
                this.sprite.position.y = this.oldPositionY;
                this.sprite.frameName = this.frameMain;
            }

            if (this.placeHolder !== undefined) {
                Dakra.towerHolderGroup.remove(this.placeHolder);
                this.placeHolder.kill();
            }
        }
    }

    goBullet() {
        if (this.lastBulletShotAt === undefined) this.lastBulletShotAt = 0;
        if (Dakra.game.time.now - this.lastBulletShotAt < this.SHOT_DELAY) return;
        this.lastBulletShotAt = Dakra.game.time.now;
        if (this.TYPE == 1) new BulletA(this);
        if (this.TYPE == 2) new BulletB(this);
        if (this.TYPE == 3) new BulletC(this);
        if (this.TYPE == 4) new BulletD(this);
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

                if (this.placeHolder !== undefined) {
                    Dakra.towerHolderGroup.remove(this.placeHolder);
                    this.placeHolder.kill();
                }
                this.placeHolder = Dakra.towerHolderGroup
                    .create(x * 40 + 20, y * 40 + 20, 'towers', this.frameHolder);
                this.placeHolder.anchor.setTo(0.5, 0.5);
            } else {
                this.sprite.frameName = this.frameError;

                if (this.placeHolder !== undefined) {
                    Dakra.towerHolderGroup.remove(this.placeHolder);
                    this.placeHolder.kill();
                }
            }
        }
    }
}
