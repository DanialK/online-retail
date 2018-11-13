import React, { useState } from 'react';
import { Query } from 'react-apollo';
import gql from 'graphql-tag';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import AsyncSelect from 'react-select/lib/Async';
import './App.css';

const loadOptions = (client) => async (inputValue, callback) => {
  const response = await client
    .query({
      query: gql`
        {
          productSearch(searchQuery: "${inputValue}") {
            stockCode,
            description
          }
        }
      `
    });

  return response.data.productSearch.map(x => ({
    value: x.stockCode,
    label: x.description
  }))
}

function App(props) {
  const { client } = props;
  const [selectedProduct, setSelectedProduct] = useState(null);

  return (
    <div className="app">
      <h1> Online Retail</h1>
      <div>
        <AsyncSelect
          isClearable
          isSearchable
          cacheOptions
          loadOptions={loadOptions(client)}
          noOptionsMessage={() => null}
          onChange={(selectedProduct) => {
            setSelectedProduct(Array.isArray(selectedProduct) ? null : selectedProduct);
          }}
        />
      </div>
      {selectedProduct && (
        <Query
          query={gql`
              {
                product(stockCode: "${selectedProduct.value}") {
                  stockCode,
                  measures(startDate: "0", endDate: "0") {
                    sales,
                    avgUnitPrice,
                    weekEnding
                  }
                }
              }
            `}
        >
          {({ loading, error, data }) => {
            if (loading) return <p>Loading...</p>;
            if (error) return <p>Error :(</p>;

            const chartData = data.product && data.product.measures
              .sort((x, y) => Number(x.weekEnding) > Number(y.weekEnding) ? 1 : -1)
              .map(x => ({
                weekEnding: new Date(Number(x.weekEnding)).toLocaleDateString(),
                sales: x.sales,
                avgUnitPrice: x.avgUnitPrice,
              }));

            if (!chartData) {
              return <p>Error :(</p>;
            }

            return (
              <LineChart width={1000} height={500} data={chartData} className="chart-container">
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="weekEnding" />
                <YAxis yAxisId="left" />
                <YAxis yAxisId="right" orientation="right" />
                <Tooltip />
                <Legend />
                <Line yAxisId="left" type="monotone" dataKey="sales" name="Sales" stroke="#8884d8" activeDot={{ r: 8 }} />
                <Line yAxisId="right" type="monotone" dataKey="avgUnitPrice" name="Average Unit Price" stroke="#82ca9d" />
              </LineChart>
            );
          }}
        </Query>
      )}

    </div>
  );
}

export default App;
