class Bullet {
    constructor() {
        this.sprite = Dakra.towerBulletGroup.create(0, 0, 'bullets');
        this.sprite.father = this;
        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.checkWorldBounds = true;
        this.sprite.outOfBoundsKill = true;
        this.sprite.kill();
    }

    revive(tower) {
        this.tower = tower;
        this.sprite.reset(tower.sprite.position.x, tower.sprite.position.y);
        this.sprite.frameName = 'beam-' + tower.TYPE + '.png';
        this.sprite.rotation = tower.sprite.rotation;
        this.sprite.body.velocity.x = Math.cos(this.sprite.rotation) * tower.BULLET_SPEED;
        this.sprite.body.velocity.y = Math.sin(this.sprite.rotation) * tower.BULLET_SPEED;

        this.damage = tower.DAMAGE;
        this.slow = tower.SLOW;
        this.armorPen = tower.ARMOR_PEN;
    }
}
