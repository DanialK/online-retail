const { client } = require("../utils/elasticsearch");

class MeasureRepository {
  async getMeasures(stockCode, type, startDate, endDate) {
    const response = await client.search({
      index: "weeklysales",
      body: {
        size: 1000,
        query: {
          bool: {
            must: [
              {
                match: { stockCode }
              },
              {
                range: {
                    weekEnding: {
                        gte: 1291726800000,
                        lte: 1323781200000
                    }
                }
              }
            ]
          }
        },
        aggs: {
          "52_week_sales": {
            sum: { field: "sales" }
          },
          // "52_week_volume": {
          //   sum: { field: "volume" }
          // },
          // "52_week_average_unit_price": {
          //   avg: { field: "avgUnitPrice" }
          // }
        }
      }
    });

    return response.hits.hits.map(x => x._source);
  }
}

module.exports = MeasureRepository;
