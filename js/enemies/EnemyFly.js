class EnemyFly extends Enemy {
    constructor() {
        super(9, 100, 0, 2000, 40);
        // var numType = 9;
        // var coin = 100;
        // var armor = 0;
        var health = 2000;
        // var speed = 40;
        this.arrayPoint = Dakra.MAP.arrayPointFly[
            Math.floor(Math.random() * Dakra.MAP.arrayPointFly.length)
        ];
        // this.currPos = 0;
        // this.nextPos = 1;
        Dakra.enemyGroup.remove(this.sprite);
        this.sprite = Dakra.enemyGroup.create(
            (this.arrayPoint[this.currPos].x + 0.5) * Dakra.configs.UNIT,
            (this.arrayPoint[this.currPos].y + 0.5) * Dakra.configs.UNIT,
            'chars'
        );
        this.sprite.frameName = 'char-1-goup-2.png'
        // this.mainFrame = 'char-' + numType + '-godown-2.png';
        // this.TYPE = numType;
        this.sprite.father = this;
        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.inputEnabled = true;
        this.sprite.events.onInputDown.add(this.onInputDown, this);
        // this.setupAnimation(numType);
        //
        // this.COIN = coin + 10 * Math.floor(Dakra.enemyGroup.length / 10);
        // this.armor = armor;
        this.sprite.health = health + 100 * Math.floor(Dakra.enemyGroup.length / 10);
        // this.movementSpeed = speed; // MAX = 300
    }
}
