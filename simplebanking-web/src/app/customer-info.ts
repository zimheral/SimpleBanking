export interface CustomerInfo {
    name: string,
    surname: string
    account: {
        iban: string,
        balance: number,
        transactions: Transaction[],
        accountType: string

    }
}

interface Transaction {
    id: number,
    amount: number
}
