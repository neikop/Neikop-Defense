class BulletD {
    constructor(tower) {
        this.sprite = Dakra.towerBulletGroupD.create(0, 0, 'bullets', 'beam-4.png');
        this.sprite.father = this;
        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.checkWorldBound = true;
        this.sprite.outOfBoundsKill = true;

        this.sprite.reset(tower.sprite.position.x, tower.sprite.position.y);
        this.sprite.rotation = tower.sprite.rotation;
        this.sprite.body.velocity.x = Math.cos(this.sprite.rotation) * tower.BULLET_SPEED;
        this.sprite.body.velocity.y = Math.sin(this.sprite.rotation) * tower.BULLET_SPEED;

        this.damage = tower.DAMAGE;
        this.slow = tower.SLOW;
        this.armorPen = tower.ARMOR_PEN;
    }
}
