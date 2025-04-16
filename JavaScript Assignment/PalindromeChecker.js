function isPalindrome(str) {
    let reversed = str.split('').reverse().join('');
    return str === reversed;
}
console.log("Is 'madam' a palindrome?", isPalindrome("madam"));