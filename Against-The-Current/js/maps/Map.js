class Map {
    constructor(arrayMap, arrayPoint) {
        this.arrayMap = arrayMap;
        this.arrayPoint = arrayPoint;

        this.width = this.arrayMap.length;
        this.height = this.arrayMap[0].length;

        for (var y = 0; y < this.height; y++) {
            for (var x = 0; x < this.width; x++) {
                var z = this.arrayMap[y][x];
                Dakra.game.add.sprite(x * Dakra.configs.UNIT, y * Dakra.configs.UNIT,
                    'maps', '' + z + '.png');
            }
        }

        for (var i = 0; i < this.arrayMap.length; i++)
            for (var j = 0; j < 3; j++)
                this.arrayMap[i].push(0);
    }
}
