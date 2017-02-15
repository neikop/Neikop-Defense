class MapC extends Map{
    constructor() {
        var arrayMap = [
            [0, 0, 2, 0, 1, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 1],
            [0, 1, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2],
            [1, 0, 0, 1, 0, 2, 0, 1, 2, 0, 0, 0],
            [0, 0, 1, 0, 0, 2, 0, 0, 2, 0, 1, 0],
            [0, 0, 2, 2, 2, 2, 0, 0, 2, 2, 2, 0],
            [2, 2, 2, 0, 0, 0, 1, 0, 0, 0, 2, 1],
            [0, 0, 2, 0, 1, 0, 0, 1, 0, 0, 2, 0],
            [0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0],
            [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 1],
            [0, 0, 1, 0, 0, 1, 0, 0, 0, 2, 0, 0]
        ];
        var arrayPoint = [];
        arrayPoint.push([
            new Phaser.Point(0, 2),
            new Phaser.Point(2, 2),
            new Phaser.Point(2, 5),
            new Phaser.Point(5, 5),
            new Phaser.Point(5, 2),
            new Phaser.Point(8, 2),
            new Phaser.Point(8, 9),
            new Phaser.Point(10, 9),
        ]);
        arrayPoint.push([
            new Phaser.Point(6, 0),
            new Phaser.Point(6, 2),
            new Phaser.Point(8, 2),
            new Phaser.Point(8, 10),
            new Phaser.Point(5, 10),
            new Phaser.Point(5, 8),
            new Phaser.Point(2, 8),
            new Phaser.Point(2, 11),
        ]);
        super(arrayMap, arrayPoint);
    }
}
