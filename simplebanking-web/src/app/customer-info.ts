export interface CustomerInfo {
    name: string,
    surname: string
    account: Account
}

export interface Account {
    iban: string,
    balance: number,
    transactions: Transaction[],
    accountType: string
}

interface Transaction {
    id: number,
    amount: number
}
