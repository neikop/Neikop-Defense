class BulletBounce {
    constructor() {
        this.sprite = Dakra.bulletBounceGroup.create(0, 0, 'bullets', 'beam-5.png');
        this.sprite.father = this;
        this.sprite.anchor.setTo(0, 0.5);
        this.sprite.checkWorldBounds = true;
        this.sprite.outOfBoundsKill = true;
        this.sprite.kill();
        this.counter = 0;
    }

    revive(enemyOne, enemyTwo, tower, number) {
        if (enemyOne === undefined | enemyTwo === undefined) return;
        this.tower = tower;
        this.enemyTwo = enemyTwo;
        this.number = number;
        this.sprite.reset(enemyOne.sprite.position.x, enemyOne.sprite.position.y);
        this.sprite.rotation = Dakra.game.physics.arcade
            .angleBetween(enemyOne.sprite, enemyTwo.sprite);

        var x = enemyOne.sprite.position.x - enemyTwo.sprite.position.x;
        var y = enemyOne.sprite.position.y - enemyTwo.sprite.position.y;
        var distance = Math.sqrt(x * x + y * y);
        var rate = distance / 30;
        this.sprite.scale.setTo(rate, 1);

        this.damage = tower.DAMAGE;
        this.slow = tower.SLOW;
        this.armorPen = tower.ARMOR_PEN;

        this.goNextTarret();
    }

    goNextTarret() {
        if (this.number != 1) return;
        var range = 100;
        var target;
        var ignore;
        for (var i = 0; i < Dakra.enemies.length; i++) {
            if (!Dakra.enemies[i].sprite.alive) continue;
            var x = this.enemyTwo.sprite.position.x - Dakra.enemies[i].sprite.position.x;
            var y = this.enemyTwo.sprite.position.y - Dakra.enemies[i].sprite.position.y;
            var distance = Math.sqrt(x * x + y * y);
            if (distance < range & distance != 0) {
                range = distance;
                target = Dakra.enemies[i];
                ignore = i;
            }
        }
        if (target === undefined) return;
        range = 100;
        var target2;
        for (var i = 0; i < Dakra.enemies.length; i++) {
            if (!Dakra.enemies[i].sprite.alive) continue;
            var x = target.sprite.position.x - Dakra.enemies[i].sprite.position.x;
            var y = target.sprite.position.y - Dakra.enemies[i].sprite.position.y;
            var distance = Math.sqrt(x * x + y * y);
            if (distance < range & distance != 0 & i != ignore) {
                range = distance;
                target2 = Dakra.enemies[i];
            }
        }
        Dakra.bulletBounceGroup.getFirstDead().father
            .revive(this.enemyTwo, target, this.tower, 0);

        Dakra.bulletBounceGroup.getFirstDead().father
            .revive(target, target2, this.tower, 0);
    }

    update() {
        if (this.counter++ == 2) {
            this.enemyTwo.beShot(this);
            this.sprite.kill();
            this.counter = 0;
        }
    }
}
